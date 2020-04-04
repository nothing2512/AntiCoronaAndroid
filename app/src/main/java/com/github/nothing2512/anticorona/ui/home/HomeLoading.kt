package com.github.nothing2512.anticorona.ui.home

import com.github.nothing2512.anticorona.databinding.FragmentHomeBinding
import com.github.nothing2512.anticorona.parent.ParentLoading

class HomeLoading(
    private val binding: FragmentHomeBinding
) : ParentLoading() {

    fun startAll() {
        indonesianStart()
        provinceStart()
        globalStart()
        countryStart()
    }

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

    fun globalStop() {
        binding.apply {
            stop(
                sfHomeGlobal,
                tvHomeGlobalCase,
                tvHomeGCase,
                tvHomeGRecovered,
                tvHomeGDeath,
                sbHomeRecovered,
                sbHomeDeath
            )
        }
    }

    fun provinceStart() {
        binding.apply {
            start(
                sfHomeProvince,
                tvHomeProvinceTitle,
                imHomeProvinceDivider,
                rvHomeProvince,
                tvHomeProvinceHighlight,
                tvHomeProvinceHighlight
            )
        }
    }

    fun provinceStop() {
        binding.apply {
            stop(
                sfHomeProvince,
                tvHomeProvinceTitle,
                imHomeProvinceDivider,
                rvHomeProvince,
                tvHomeProvinceHighlight,
                tvHomeProvinceHighlight
            )
        }
    }

    fun countryStart() {
        binding.apply {
            start(
                sfHomeCountry,
                rvHomeGlobal,
                tvHomeGlobalHighlight
            )
        }
    }

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