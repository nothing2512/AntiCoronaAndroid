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

package com.github.nothing2512.anticorona.ui.province

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse
import com.github.nothing2512.anticorona.repositories.CaseRepository
import com.github.nothing2512.anticorona.utils.launchMain

/**
 * [ProvinceViewModel] class
 * @author Robet Atiq Maulana Rifqi
 * @constructor [caseRepository]
 * @see CaseRepository
 * @see ViewModel
 */
class ProvinceViewModel(private val caseRepository: CaseRepository) : ViewModel() {

    /**
     * Declaring read only variable
     *
     * @see CaseRepository.provinceCase
     * @see MutableLiveData
     * @see CaseResponse
     */
    val repoCase = caseRepository.provinceCase

    private val _cases = MutableLiveData<List<CaseResponse>>()
    val cases: LiveData<List<CaseResponse>>
        get() = _cases

    /**
     * Get province cases data from repository
     * using coroutines to execute code
     *
     * @see launchMain
     * @see CaseRepository.getProvinceCase
     */
    fun getCases() {
        launchMain { caseRepository.getProvinceCase() }
    }

    /**
     * Set cases data
     * @see LiveData.postValue
     */
    fun setCases(data: List<CaseResponse>) {
        if(data != _cases.value) _cases.postValue(data)
    }
}