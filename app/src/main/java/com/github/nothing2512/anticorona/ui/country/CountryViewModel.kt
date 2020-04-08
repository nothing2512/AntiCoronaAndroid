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

package com.github.nothing2512.anticorona.ui.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse
import com.github.nothing2512.anticorona.repositories.CaseRepository
import com.github.nothing2512.anticorona.utils.launchMain

/**
 * [CountryViewModel] class
 * @author Robet Atiq Maulana Rifqi
 *
 * @constructor [caseRepository]
 *
 * @see CaseRepository
 * @see ViewModel
 */
class CountryViewModel(private val caseRepository: CaseRepository) : ViewModel() {

    /**
     * Declaring read only variable
     *
     * @see CaseRepository.countriesCase
     * @see MutableLiveData
     * @see CaseResponse
     */
    val repoCases = caseRepository.countriesCase
    private val _cases = MutableLiveData<List<CaseResponse>>()
    val cases: LiveData<List<CaseResponse>>
        get() = _cases

    /**
     * getting corona virus data cases by countries
     * using [viewModelScope] to execute code
     *
     * @see launchMain
     * @see CaseRepository.getCountriesCase
     */
    fun getCases() {
        launchMain { caseRepository.getCountriesCase() }
    }

    /**
     * setting corona virus data cases to [_cases]
     * @param data
     * @see LiveData.postValue
     */
    fun setCases(data: List<CaseResponse>) {
        if (_cases.value != data) _cases.postValue(data)
    }
}