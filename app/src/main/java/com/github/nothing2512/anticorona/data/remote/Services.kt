package com.github.nothing2512.anticorona.data.remote

import androidx.lifecycle.LiveData
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse
import com.github.nothing2512.anticorona.data.remote.response.FaqsResponse
import com.github.nothing2512.anticorona.data.remote.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Services {

    @GET("cases/indonesia")
    fun getIndonesianCase(): LiveData<ApiResponse<CaseResponse>>

    @GET("cases/indonesia/province")
    fun getProvinceCases(): LiveData<ApiResponse<List<CaseResponse>>>

    @GET("cases/list")
    fun getCountriesCase(): LiveData<ApiResponse<List<CaseResponse>>>

    @GET("cases/global")
    fun getGlobalCases(): LiveData<ApiResponse<CaseResponse>>

    @GET("faqs")
    fun getFaqs(@Query("lang") lang: String): LiveData<ApiResponse<List<FaqsResponse>>>

    @GET("news")
    fun getNews(@Query("lang") lang: String): LiveData<ApiResponse<List<NewsResponse>>>
}