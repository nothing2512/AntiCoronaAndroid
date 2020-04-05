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

package com.github.nothing2512.anticorona.ui.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.nothing2512.anticorona.repositories.CaseRepository
import com.github.nothing2512.anticorona.ui.faqs.FaqsFragment
import com.github.nothing2512.anticorona.ui.news.NewsFragment
import com.github.nothing2512.anticorona.utils.launchMain

/**
 * [HomeViewModel] class
 * @author Robet Atiq MAulana Rifqi
 * @constructor [caseRepository]
 * @see CaseRepository
 * @see ViewModel
 */
class HomeViewModel(private val caseRepository: CaseRepository) : ViewModel() {

    /**
     * Declaring read only variable
     *
     * @see FaqsFragment.newInstance
     * @see HomeFragment.newInstance
     * @see NewsFragment.newInstance
     * @see MutableLiveData
     * @see Fragment
     * @see CaseRepository
     */
    private val _fragmentList = listOf(
        FaqsFragment.newInstance(),
        HomeFragment.newInstance(),
        NewsFragment.newInstance()
    )
    private val _fragment = MutableLiveData<Fragment>()

    val fragment: LiveData<Fragment>
        get() = _fragment

    val globalCase = caseRepository.globalCase
    val indonesianCase = caseRepository.indonesianCase
    val provinceCase = caseRepository.provinceCase
    val countryCase = caseRepository.countriesCase

    /**
     * getting global case data
     * using coroutines to execute code
     * @see launchMain
     * @see CaseRepository.getGlobalCase
     */
    fun getGlobalCase() {
        launchMain { caseRepository.getGlobalCase() }
    }

    /**
     * getting indonesian case data
     * using coroutines to execute code
     * @see launchMain
     * @see CaseRepository.getIndonesianCase
     */
    fun getIndonesianCase() {
        launchMain { caseRepository.getIndonesianCase() }
    }

    /**
     * getting province case data
     * using coroutines to execute code
     * @see launchMain
     * @see CaseRepository.getProvinceCase
     */
    fun getProvinceCase() {
        launchMain { caseRepository.getProvinceCase() }
    }

    /**
     * getting country case data
     * using coroutines to execute code
     * @see launchMain
     * @see CaseRepository.getCountriesCase
     */
    fun getCountryCase() {
        launchMain { caseRepository.getCountriesCase() }
    }

    /**
     * setting fragment value
     * @see LiveData.postValue
     */
    fun setFragment(i: Int) {
        if (_fragment.value != _fragmentList[i]) _fragment.postValue(_fragmentList[i])
    }
}