/*
 * Copyright 2020 Nothing
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nothing2512.anticorona.utils

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.github.nothing2512.anticorona.data.remote.ApiEmptyResponse
import com.github.nothing2512.anticorona.data.remote.ApiErrorResponse
import com.github.nothing2512.anticorona.data.remote.ApiResponse
import com.github.nothing2512.anticorona.data.remote.ApiSuccessResponse
import com.github.nothing2512.anticorona.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * [BoundService] class
 * @author Robet Atiq Maulana Rifqi
 * @constructor [appExecutors]
 */
abstract class BoundService<TYPE>
@MainThread constructor(private val appExecutors: AppExecutors) {

    /**
     * Declaring result variable
     * @see MediatorLiveData
     */
    private val result = MediatorLiveData<Resource<TYPE>>()

    /**
     * fetching data from remote repository
     * using [AppExecutors] to executing code
     */
    private fun fetch() {

        setValue(Resource.loading(null))

        val apiResponse = createCall()

        result.addSource(apiResponse) { response ->

            result.removeSource(apiResponse)

            when (response) {

                is ApiSuccessResponse -> {
                    appExecutors.diskIO.execute {
                        saveCallResult(response.body)
                        val process = response.body
                        appExecutors.mainThread.execute { setValue(Resource.success(process)) }
                    }

                }
                is ApiEmptyResponse -> {
                    appExecutors
                        .mainThread
                        .execute { setValue(Resource.error("No responses", null)) }
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    setValue(Resource.error(response.errorMessage, null))
                }
            }
        }
    }

    /**
     * set new result value
     * @param newValue
     */
    @MainThread
    private fun setValue(newValue: Resource<TYPE>) {
        if (result.value != newValue) result.value = newValue
    }

    /**
     * triggered function when fetch is failed
     */
    protected open fun onFetchFailed() {}

    /**
     * getting result as live data
     * using [AppExecutors] to executing coroutine
     * @return
     */
    suspend fun asLiveData(): LiveData<Resource<TYPE>> = withContext(Dispatchers.IO) {
        appExecutors.mainThread.execute {
            val dbSource = loadFromDb()
            result.addSource(dbSource) { data ->
                result.removeSource(dbSource)
                if (data is List<*>) {
                    if (data.size == 0) fetch()
                    else setValue(Resource.success(data))
                } else {
                    if (data == null) fetch()
                    else setValue(Resource.success(data))
                }
            }
        }
        result
    }

    /**
     * Creating call services to get response from remote repository
     * @return
     */
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<TYPE>>

    /**
     * Getting data from in app database
     * @return
     */
    protected abstract fun loadFromDb(): LiveData<TYPE>

    /**
     * Saving result data from remote services to in app database
     * @param item
     */
    protected abstract fun saveCallResult(item: TYPE)
}