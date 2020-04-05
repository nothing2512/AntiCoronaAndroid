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

package com.github.nothing2512.anticorona.vo

/**
 * [Resource] class
 * @author Robet Atiq Maulana Rifqi
 * @constructor [status], [data], [message]
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {

        /**
         * function success
         * provide success response
         *
         * @param data
         *
         * @see Status.SUCCESS
         *
         * @return Resource
         */
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        /**
         * function success
         * provide success response
         *
         * @param msg
         * @param data
         *
         * @see Status.ERROR
         *
         * @return Resource
         */
        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        /**
         * function success
         * provide success response
         *
         * @param data
         *
         * @see Status.LOADING
         *
         * @return Resource
         */
        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}