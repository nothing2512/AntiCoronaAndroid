package com.github.nothing2512.anticorona.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.nothing2512.anticorona.data.local.dao.CasesDao
import com.github.nothing2512.anticorona.data.local.entity.CountryEntity
import com.github.nothing2512.anticorona.data.local.entity.GlobalEntity
import com.github.nothing2512.anticorona.data.local.entity.IndonesianEntity
import com.github.nothing2512.anticorona.data.local.entity.ProvinceEntity
import com.github.nothing2512.anticorona.data.remote.Services
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse
import com.github.nothing2512.anticorona.utils.AppExecutors
import com.github.nothing2512.anticorona.utils.BoundService
import com.github.nothing2512.anticorona.utils.idle
import com.github.nothing2512.anticorona.vo.Resource
import com.github.nothing2512.anticorona.vo.Status

class CaseRepository(
    private val appExecutors: AppExecutors,
    private val services: Services,
    private val casesDao: CasesDao
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
        object : BoundService<CaseResponse>(appExecutors) {

            override fun createCall() = idle { services.getIndonesianCase() }

            override fun loadFromDb(): LiveData<CaseResponse> {
                val data = MutableLiveData<CaseResponse>()
                casesDao.getIndonesian()?.observeForever {
                    try {
                        data.postValue(it.toResponse())
                    } catch (e: Exception) {
                        data.postValue(null)
                    }
                }
                return data
            }

            override fun saveCallResult(item: CaseResponse) {
                casesDao.deleteIndonesian()
                casesDao.insertIndonesian(IndonesianEntity.parse(item))
            }

        }.asLiveData().observeForever {
            if (it.status == Status.SUCCESS) {
                if (indonesianCase.value == null) _indonesianCase.postValue(it)
                if (it != indonesianCase.value) _indonesianCase.postValue(it)
            } else if(it.status == Status.ERROR && indonesianCase.value == null) _indonesianCase.postValue(it)
            else _indonesianCase.postValue(it)
        }
    }

    suspend fun getGlobalCase() {
        object : BoundService<CaseResponse>(appExecutors) {

            override fun createCall() = idle { services.getGlobalCases() }

            override fun loadFromDb(): LiveData<CaseResponse> {
                val data = MutableLiveData<CaseResponse>()
                casesDao.getGlobal()?.observeForever {
                    try {
                        data.postValue(it.toResponse())
                    } catch (e: Exception) {
                        data.postValue(null)
                    }
                }
                return data
            }

            override fun saveCallResult(item: CaseResponse) {
                casesDao.deleteGlobal()
                casesDao.insertGlobal(GlobalEntity.parse(item))
            }
        }.asLiveData().observeForever {
            if (it.status == Status.SUCCESS) {
                if (_globalCase.value == null) _globalCase.postValue(it)
                if (it != _globalCase.value) _globalCase.postValue(it)
            } else if(it.status == Status.ERROR && _globalCase.value == null) _globalCase.postValue(it)
            else _globalCase.postValue(it)
        }
    }

    suspend fun getProvinceCase() {
        object : BoundService<List<CaseResponse>>(appExecutors) {

            override fun createCall() = idle { services.getProvinceCases() }

            override fun loadFromDb(): LiveData<List<CaseResponse>> {
                val data = MutableLiveData<List<CaseResponse>>()
                casesDao.getProvinces()?.observeForever {
                    val provinces = ArrayList<CaseResponse>()
                    it.forEach { item -> provinces.add(item.toResponse()) }
                    data.postValue(provinces)
                }
                return data
            }

            override fun saveCallResult(item: List<CaseResponse>) {
                casesDao.deleteProvinces()
                item.forEach { casesDao.insertProvinces(ProvinceEntity.parse(it)) }
            }
        }.asLiveData().observeForever {
            if (it.status == Status.SUCCESS) {
                if (_provinceCase.value == null) _provinceCase.postValue(it)
                if (_provinceCase.value != it) _provinceCase.postValue(it)
            } else if(it.status == Status.ERROR && _provinceCase.value == null) _provinceCase.postValue(it)
            else _provinceCase.postValue(it)
        }
    }

    suspend fun getCountriesCase() {
        object : BoundService<List<CaseResponse>>(appExecutors) {

            override fun createCall() = idle { services.getCountriesCase() }

            override fun loadFromDb(): LiveData<List<CaseResponse>> {
                val data = MutableLiveData<List<CaseResponse>>()
                casesDao.getCountries()?.observeForever {
                    val countries = ArrayList<CaseResponse>()
                    it.forEach { item -> countries.add(item.toResponse()) }
                    data.postValue(countries)
                }
                return data
            }

            override fun saveCallResult(item: List<CaseResponse>) {
                casesDao.deleteCountries()
                item.forEach { casesDao.insertCountries(CountryEntity.parse(it)) }
            }
        }.asLiveData().observeForever {
            if (it.status == Status.SUCCESS) {
                if (_countriesCase.value == null) _countriesCase.postValue(it)
                if (_countriesCase.value != it) _countriesCase.postValue(it)
            } else if(it.status == Status.ERROR && _countriesCase.value == null) _countriesCase.postValue(it)
            else _countriesCase.postValue(it)
        }
    }
}