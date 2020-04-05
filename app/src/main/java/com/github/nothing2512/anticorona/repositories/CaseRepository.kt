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

/**
 * [CaseRepository] class
 * @author Robet Atiq Maulana Rifqi
 *
 * @constructor [appExecutors], [services], [casesDao]
 *
 * @see AppExecutors
 * @see Services
 * @see CasesDao
 */
class CaseRepository(
    private val appExecutors: AppExecutors,
    private val services: Services,
    private val casesDao: CasesDao
) {

    /**
     * declaring private read only variable
     *
     * @see MutableLiveData
     * @see Resource
     * @see CaseResponse
     */
    private val _indonesianCase = MutableLiveData<Resource<CaseResponse>>()
    private val _globalCase = MutableLiveData<Resource<CaseResponse>>()
    private val _provinceCase = MutableLiveData<Resource<List<CaseResponse>>>()
    private val _countriesCase = MutableLiveData<Resource<List<CaseResponse>>>()

    /**
     * declaring public read only variable
     *
     * @see LiveData
     * @see Resource
     * @see CaseResponse
     */
    val indonesianCase: LiveData<Resource<CaseResponse>>
        get() = _indonesianCase

    val globalCase: LiveData<Resource<CaseResponse>>
        get() = _globalCase

    val provinceCase: LiveData<Resource<List<CaseResponse>>>
        get() = _provinceCase

    val countriesCase: LiveData<Resource<List<CaseResponse>>>
        get() = _countriesCase

    /**
     * getting corona virus cases data in indonesia
     */
    suspend fun getIndonesianCase() {

        /**
         * Bouncing Services
         * @see BoundService
         */
        object : BoundService<CaseResponse>(appExecutors) {

            /**
             * Getting data from remote repository
             * @see Services.getIndonesianCase
             * @see idle
             * @return
             */
            override fun createCall() = idle { services.getIndonesianCase() }

            /**
             * Load data from in app database
             * @see CasesDao.getIndonesian
             * @return
             */
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

            /**
             * saving remote repository result to in app database
             * @param item
             * @see CasesDao.insertIndonesian
             * @return
             */
            override fun saveCallResult(item: CaseResponse) {
                casesDao.insertIndonesian(IndonesianEntity.parse(item))
            }

        }.asLiveData().observeForever {
            if (it.status == Status.SUCCESS) {
                if (indonesianCase.value == null) _indonesianCase.postValue(it)
                if (it != indonesianCase.value) _indonesianCase.postValue(it)
            } else if (it.status == Status.ERROR && indonesianCase.value == null) _indonesianCase.postValue(
                it
            )
            else _indonesianCase.postValue(it)
        }
    }

    /**
     * getting global corona virus cases data
     */
    suspend fun getGlobalCase() {

        /**
         * bouncing services
         * @see BoundService
         */
        object : BoundService<CaseResponse>(appExecutors) {

            /**
             * Getting data from remote repository
             * @see Services.getGlobalCases
             * @see idle
             * @return
             */
            override fun createCall() = idle { services.getGlobalCases() }

            /**
             * Load data from in app database
             * @see CasesDao.getGlobal
             * @return
             */
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

            /**
             * Saving result from remote repository to in app database
             * @see CasesDao.insertGlobal
             */
            override fun saveCallResult(item: CaseResponse) {
                casesDao.insertGlobal(GlobalEntity.parse(item))
            }
        }.asLiveData().observeForever {
            if (it.status == Status.SUCCESS) {
                if (_globalCase.value == null) _globalCase.postValue(it)
                if (it != _globalCase.value) _globalCase.postValue(it)
            } else if (it.status == Status.ERROR && _globalCase.value == null) _globalCase.postValue(
                it
            )
            else _globalCase.postValue(it)
        }
    }

    /**
     * getting corona virus cases data in indonesia by province
     */
    suspend fun getProvinceCase() {

        /**
         * bouncing services
         * @see BoundService
         */
        object : BoundService<List<CaseResponse>>(appExecutors) {

            /**
             * Getting data from remote services
             * @see Services.getProvinceCases
             * @see idle
             * @return
             */
            override fun createCall() = idle { services.getProvinceCases() }

            /**
             * Getting data from in app database
             * @see CasesDao.getProvinces
             * @return
             */
            override fun loadFromDb(): LiveData<List<CaseResponse>> {
                val data = MutableLiveData<List<CaseResponse>>()
                casesDao.getProvinces()?.observeForever {
                    val provinces = ArrayList<CaseResponse>()
                    it.forEach { item -> provinces.add(item.toResponse()) }
                    data.postValue(provinces)
                }
                return data
            }

            /**
             * Saving result from remote repository to in app database
             * @see CasesDao.insertProvinces
             */
            override fun saveCallResult(item: List<CaseResponse>) {
                item.forEach { casesDao.insertProvinces(ProvinceEntity.parse(it)) }
            }
        }.asLiveData().observeForever {
            if (it.status == Status.SUCCESS) {
                if (_provinceCase.value == null) _provinceCase.postValue(it)
                if (_provinceCase.value != it) _provinceCase.postValue(it)
            } else if (it.status == Status.ERROR && _provinceCase.value == null) _provinceCase.postValue(
                it
            )
            else _provinceCase.postValue(it)
        }
    }

    /**
     * getting global corona virus cases data by countries
     */
    suspend fun getCountriesCase() {

        /**
         * Bouncing services
         * @see BoundService
         */
        object : BoundService<List<CaseResponse>>(appExecutors) {

            /**
             * Getting data from remote repository
             * @see Services.getCountriesCase
             * @see idle
             */
            override fun createCall() = idle { services.getCountriesCase() }

            /**
             * Getting data from in app database
             * @see CasesDao.getCountries
             * @return
             */
            override fun loadFromDb(): LiveData<List<CaseResponse>> {
                val data = MutableLiveData<List<CaseResponse>>()
                casesDao.getCountries()?.observeForever {
                    val countries = ArrayList<CaseResponse>()
                    it.forEach { item -> countries.add(item.toResponse()) }
                    data.postValue(countries)
                }
                return data
            }

            /**
             * Saving result from remote repository to in app database
             * @see CasesDao.insertCountries
             */
            override fun saveCallResult(item: List<CaseResponse>) {
                item.forEach { casesDao.insertCountries(CountryEntity.parse(it)) }
            }
        }.asLiveData().observeForever {
            if (it.status == Status.SUCCESS) {
                if (_countriesCase.value == null) _countriesCase.postValue(it)
                if (_countriesCase.value != it) _countriesCase.postValue(it)
            } else if (it.status == Status.ERROR && _countriesCase.value == null) _countriesCase.postValue(
                it
            )
            else _countriesCase.postValue(it)
        }
    }
}