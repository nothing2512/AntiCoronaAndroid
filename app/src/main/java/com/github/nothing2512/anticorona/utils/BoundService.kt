package com.github.nothing2512.anticorona.utils

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.github.nothing2512.anticorona.data.remote.ApiEmptyResponse
import com.github.nothing2512.anticorona.data.remote.ApiErrorResponse
import com.github.nothing2512.anticorona.data.remote.ApiResponse
import com.github.nothing2512.anticorona.data.remote.ApiSuccessResponse
import com.github.nothing2512.anticorona.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BoundService<TYPE>
@MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<TYPE>>()

    private fun fetch() {

        val apiResponse = createCall()

        result.addSource(apiResponse) { response ->

            result.removeSource(apiResponse)

            when (response) {

                is ApiSuccessResponse -> {
                    appExecutors.diskIO.execute {
                        saveCallResult(response.body)
                        val process = processResponse(response)
                        appExecutors.mainThread.execute {
                            setValue(Resource.success(process))
                        }
                    }

                }
                is ApiEmptyResponse -> {
                    appExecutors.mainThread.execute {
                        setValue(Resource.error("No responses", null))
                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    setValue(Resource.error(response.errorMessage, null))
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<TYPE>) {
        if (result.value != newValue) result.value = newValue
    }

    protected open fun onFetchFailed() {}

    suspend fun asLiveData(): LiveData<Resource<TYPE>> = withContext(Dispatchers.IO) {
        appExecutors.mainThread.execute {

            val dbSource = loadFromDb()
            result.addSource(dbSource) { data ->
                result.removeSource(dbSource)
                if (data is List<*>) {
                    if (data.size == 0) setValue(Resource.loading(null))
                    else setValue(Resource.success(data))
                    fetch()
                } else {
                    if (data == null) setValue(Resource.loading(null))
                    else setValue(Resource.success(data))
                    fetch()
                }
            }
        }
        result
    }

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<TYPE>) = response.body

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<TYPE>>
    
    protected abstract fun loadFromDb(): LiveData<TYPE>

    protected abstract fun saveCallResult(item: TYPE)
}