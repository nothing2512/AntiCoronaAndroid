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

package com.github.nothing2512.anticorona.data.remote

import retrofit2.Response

/**
 * [ApiResponse] class
 * @author Robet Atiq Maulana Rifqi
 */
@Suppress("unused")
sealed class ApiResponse<T> {
    companion object {

        /**
         * function create
         * Creating Error Response
         *
         * @param error
         *
         * @see ApiErrorResponse
         *
         * @return ApiErrorResponse
         */
        fun <T> create(error: Throwable): ApiErrorResponse<T> =
            ApiErrorResponse(error.message ?: "")

        /**
         * function create
         * creating response
         *
         * @param response
         *
         * @return ApiResponse
         */
        fun <T> create(response: Response<T>): ApiResponse<T> =

            /**
             * Check if response is success or not
             */
            if (response.isSuccessful) {

                /**
                 * Getting body of responses
                 */
                val body = response.body()

                /**
                 * returning responses
                 *
                 * @see ApiEmptyResponse
                 * @see ApiSuccessResponse
                 */
                if (body == null || response.code() == 204) ApiEmptyResponse()
                else ApiSuccessResponse(body)

            } else {

                /**
                 * Getting Error Message
                 */
                val errorMsg =
                    if (response.errorBody()?.string().isNullOrEmpty()) response.message()
                    else response.errorBody()?.string()

                /**
                 * returning responses
                 * @see ApiErrorResponse
                 */
                ApiErrorResponse(errorMsg ?: "unknown error")
            }
    }
}

/**
 * [ApiEmptyResponse] class
 * @see ApiResponse
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

/**
 * [ApiSuccessResponse] class
 * @constructor [body]
 * @see ApiResponse
 */
data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

/**
 * [ApiErrorResponse] class
 * @constructor [errorMessage]
 * @see ApiResponse
 */
data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()
