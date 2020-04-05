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
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * [CallAdapterFactory] class
 * @author Robet Atiq Maulana Rifqi
 * @see CallAdapter.Factory
 */
class CallAdapterFactory : CallAdapter.Factory() {

    /**
     * function get
     * getting call adapter
     *
     * @param returnType
     * @param annotations
     * @param retrofit
     *
     * @see CallAdapter.Factory.get
     * @see MainCallAdapter
     *
     * @return MainCallAdapter
     */
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        /**
         * returning null if raw return type is not live data
         */
        if (getRawType(returnType) != LiveData::class.java) return null

        /**
         * Getting parameterBound raw type
         *
         * @see CallAdapter.Factory.getParameterUpperBound
         * @see CallAdapter.Factory.getRawType
         */
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)

        /**
         * requiring observable type must be resource and parameterized
         */
        require(rawObservableType == ApiResponse::class.java) { "type must be a resource" }
        require(observableType is ParameterizedType) { "resource must be parameterized" }

        /**
         * getting body type
         * @see CallAdapter.Factory.getParameterUpperBound
         */
        val bodyType = getParameterUpperBound(0, observableType)

        return MainCallAdapter<Any>(bodyType)
    }

}