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

package com.github.nothing2512.anticorona.data.remote.adapter

import androidx.lifecycle.LiveData
import com.github.nothing2512.anticorona.data.remote.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * [MainCallAdapter] class
 * @author Robet Atiq Maulana rifqi
 *
 * @constructor [responseType]
 *
 * @see CallAdapter
 */
class MainCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, LiveData<ApiResponse<R>>> {

    /**
     * function responseType
     * getting type of responses
     *
     * @see CallAdapter.responseType
     */
    override fun responseType() = responseType

    /**
     * function adapt
     * adapting response
     *
     * @param call
     *
     * @see CallAdapter.adapt
     *
     * @return LiveData<ApiResponse<R>>
     */
    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
        return object : LiveData<ApiResponse<R>>() {

            /**
             * Initialize Read-Write variable
             */
            private var started = AtomicBoolean(false)

            /**
             * function onActive
             * triggering response after fetching data
             *
             * @see LiveData.onActive
             */
            override fun onActive() {
                super.onActive()

                /**
                 * Comparing and setting started variable
                 *
                 * @see started
                 * @see AtomicBoolean.compareAndSet
                 */
                if (started.compareAndSet(false, true)) {

                    /**
                     * Enqueuing call data
                     * @see Call.enqueue
                     */
                    call.enqueue(object : Callback<R> {

                        /**
                         * function onResponse
                         * handling response when success fetch
                         *
                         * @param call
                         * @param response
                         *
                         * @see Callback.onResponse
                         */
                        override fun onResponse(call: Call<R>, response: Response<R>) {

                            /**
                             * update data
                             *
                             * @see LiveData.postValue
                             * @see ApiResponse.create
                             */
                            postValue(ApiResponse.create(response))
                        }

                        /**
                         * function onFailure
                         * handling response when failure fetch
                         *
                         * @param call
                         * @param throwable
                         */
                        override fun onFailure(call: Call<R>, throwable: Throwable) {

                            /**
                             * update data
                             *
                             * @see LiveData.postValue
                             * @see ApiResponse.create
                             */
                            postValue(ApiResponse.create(throwable))
                        }
                    })
                }
            }
        }
    }
}