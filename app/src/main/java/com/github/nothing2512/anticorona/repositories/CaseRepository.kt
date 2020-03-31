package com.github.nothing2512.anticorona.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.nothing2512.anticorona.data.ApiResponse
import com.github.nothing2512.anticorona.data.Services
import com.github.nothing2512.anticorona.data.response.CaseResponse
import com.github.nothing2512.anticorona.utils.AppExecutors
import com.github.nothing2512.anticorona.utils.BoundService
import com.github.nothing2512.anticorona.utils.idle
import com.github.nothing2512.anticorona.vo.Resource

class CaseRepository(
    private val appExecutors: AppExecutors,
    private val services: Services
) {

    private val _indonesianCase = MutableLiveData<Resource<CaseResponse>>()
    private val _globalCase = MutableLiveData<Resource<CaseResponse>>()
    private val _provinceCase = MutableLiveData<Resource<List<CaseResponse>>>()
    private val _countriesCase = MutableLiveData<Resource<List<CaseResponse>>>()

    val indonesianCase: LiveData<Resource<CaseResponse>>
        get() = _indonesianCase

    val globalCase: LiveData<Resource<CaseResponse>>
        get() = _globalCase

    val provinceCase: LiveData<Resource<List<CaseResponse>>>
        get() = _provinceCase

    val countriesCase: LiveData<Resource<List<CaseResponse>>>
        get() = _countriesCase

    suspend fun getIndonesianCase() {
        object: BoundService<CaseResponse>(appExecutors) {
            override fun createCall() = idle { services.getIndonesianCase() }
        }.asLiveData().observeForever { _indonesianCase.postValue(it) }
    }

    suspend fun getGlobalCase() {
        object: BoundService<CaseResponse>(appExecutors) {
            override fun createCall() = idle { services.getGlobalCases() }
        }.asLiveData().observeForever { _globalCase.postValue(it) }
    }

    suspend fun getProvinceCase() {
        object: BoundService<List<CaseResponse>>(appExecutors) {
            override fun createCall() = idle { services.getProvinceCases() }
        }.asLiveData().observeForever { _provinceCase.postValue(it) }
    }

    suspend fun getCountriesCase() {
        object: BoundService<List<CaseResponse>>(appExecutors) {
            override fun createCall() = idle { services.getCountriesCase() }
        }.asLiveData().observeForever { _countriesCase.postValue(it) }
    }
}