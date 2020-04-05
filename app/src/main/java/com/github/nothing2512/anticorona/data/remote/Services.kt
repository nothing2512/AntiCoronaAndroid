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

import androidx.lifecycle.LiveData
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse
import com.github.nothing2512.anticorona.data.remote.response.FaqsResponse
import com.github.nothing2512.anticorona.data.remote.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * [Services] interface
 * @author Robet Atiq Maulana Rifqi
 */
interface Services {

    /**
     * function getIndonesianCase
     * getting corona cases data in indonesia
     *
     * @see GET
     * @see LiveData
     * @see ApiResponse
     * @see CaseResponse
     *
     * @return LiveData<ApiResponse<CaseResponse>>
     */
    @GET("cases/indonesia")
    fun getIndonesianCase(): LiveData<ApiResponse<CaseResponse>>

    /**
     * function getProvinceCases
     * getting corona cases data in indonesia by province
     *
     * @see GET
     * @see LiveData
     * @see ApiResponse
     * @see CaseResponse
     *
     * @return LiveData<ApiResponse<List<CaseResponse>>>
     */
    @GET("cases/indonesia/province")
    fun getProvinceCases(): LiveData<ApiResponse<List<CaseResponse>>>

    /**
     * function getCountriesCase
     * getting corona cases in world by countries
     *
     * @see GET
     * @see LiveData
     * @see ApiResponse
     * @see CaseResponse
     *
     * @return LiveData<ApiResponse<List<CaseResponse>>>
     */
    @GET("cases/list")
    fun getCountriesCase(): LiveData<ApiResponse<List<CaseResponse>>>

    /**
     * function getGlobalCases
     * getting corona cases in world
     *
     * @see GET
     * @see LiveData
     * @see ApiResponse
     * @see CaseResponse
     *
     * @return LiveData<ApiResponse<CaseResponse>>
     */
    @GET("cases/global")
    fun getGlobalCases(): LiveData<ApiResponse<CaseResponse>>

    /**
     * function getFaqs
     * getting faqs of app
     *
     * @param lang
     *
     * @see GET
     * @see LiveData
     * @see ApiResponse
     * @see FaqsResponse
     *
     * @return LiveData<ApiResponse<List<FaqsResponse>>
     */
    @GET("faqs")
    fun getFaqs(@Query("lang") lang: String): LiveData<ApiResponse<List<FaqsResponse>>>

    /**
     * function getNews
     * getting latest news of corona virus
     *
     * @param lang
     *
     * @see GET
     * @see LiveData
     * @see ApiResponse
     * @see NewsResponse
     *
     * @return LiveData<ApiResponse<List<NewsReponse>>>
     */
    @GET("news")
    fun getNews(@Query("lang") lang: String): LiveData<ApiResponse<List<NewsResponse>>>
}