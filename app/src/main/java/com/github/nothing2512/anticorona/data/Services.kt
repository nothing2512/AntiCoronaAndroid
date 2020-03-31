package com.github.nothing2512.anticorona.data

import androidx.lifecycle.LiveData
import com.github.nothing2512.anticorona.data.response.CaseResponse
import com.github.nothing2512.anticorona.data.response.FaqsResponse
import retrofit2.http.GET

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
    fun getFaqs(): LiveData<ApiResponse<List<FaqsResponse>>>
}