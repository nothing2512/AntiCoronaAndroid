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

import com.github.nothing2512.anticorona.databinding.FragmentHomeBinding
import com.github.nothing2512.anticorona.parent.ParentLoading

/**
 * [HomeLoading] class
 * @author Robet Atiq Maulana Rifqi
 * @constructor [binding]
 * @see ParentLoading
 */
class HomeLoading(
    private val binding: FragmentHomeBinding
) : ParentLoading() {

    /**
     * starting all loading
     * @see indonesianStart
     * @see provinceStart
     * @see globalStart
     * @see countryStart
     */
    fun startAll() {
        indonesianStart()
        provinceStart()
        globalStart()
        countryStart()
    }

    /**
     * start indonesian loading
     * @see ParentLoading.start
     */
    private fun indonesianStart() {
        binding.apply {
            start(
                sfHomeIndonesian,
                tvHomeIndonesianCase,
                homeFlag,
                imHomeICase,
                imHomeIRecovered,
                imHomeIDeath,
                tvHomeICase,
                tvHomeIRecovered,
                tvHomeIDeath,
                tvHomeICaseValue,
                tvHomeIRecoveredValue,
                tvHomeIDeathValue
            )
        }
    }

    /**
     * stopping indonesian loading
     * @see ParentLoading.stop
     */
    fun indonesianStop() {
        binding.apply {
            stop(
                sfHomeIndonesian,
                tvHomeIndonesianCase,
                homeFlag,
                imHomeICase,
                imHomeIRecovered,
                imHomeIDeath,
                tvHomeICase,
                tvHomeIRecovered,
                tvHomeIDeath,
                tvHomeICaseValue,
                tvHomeIRecoveredValue,
                tvHomeIDeathValue
            )
        }
    }

    /**
     * starting global loading
     * @see ParentLoading.start
     */
    fun globalStart() {
        binding.apply {
            start(
                sfHomeGlobal,
                tvHomeGlobalCase,
                tvHomeGCase,
                tvHomeGRecovered,
                tvHomeGDeath,
                sbHomeRecovered,
                sbHomeDeath,
                imHomeGlobal,
                imHomeGDeath,
                imHomeGRecovered
            )
        }
    }

    /**
     * starting global loading
     * @see ParentLoading.stop
     */
    fun globalStop() {
        binding.apply {
            stop(
                sfHomeGlobal,
                tvHomeGlobalCase,
                tvHomeGCase,
                tvHomeGRecovered,
                tvHomeGDeath,
                sbHomeRecovered,
                sbHomeDeath,
                imHomeGlobal,
                imHomeGDeath,
                imHomeGRecovered
            )
        }
    }

    /**
     * starting province loading
     * @see ParentLoading.start
     */
    fun provinceStart() {
        binding.apply {
            start(
                sfHomeProvince,
                tvHomeProvinceTitle,
                imHomeProvinceDivider,
                rvHomeProvince,
                tvHomeProvinceHighlight
            )
        }
    }

    /**
     * stopping province loading
     * @see ParentLoading.stop
     */
    fun provinceStop() {
        binding.apply {
            stop(
                sfHomeProvince,
                tvHomeProvinceTitle,
                imHomeProvinceDivider,
                rvHomeProvince,
                tvHomeProvinceHighlight
            )
        }
    }

    /**
     * starting country loading
     * @see ParentLoading.start
     */
    fun countryStart() {
        binding.apply {
            start(
                sfHomeCountry,
                rvHomeGlobal,
                tvHomeGlobalHighlight
            )
        }
    }

    /**
     * stopping country loading
     * @see ParentLoading.stop
     */
    fun countryStop() {
        binding.apply {
            stop(
                sfHomeCountry,
                rvHomeGlobal,
                tvHomeGlobalHighlight
            )
        }
    }
}