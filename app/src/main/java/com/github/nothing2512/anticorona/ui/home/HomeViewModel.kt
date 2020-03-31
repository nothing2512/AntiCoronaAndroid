package com.github.nothing2512.anticorona.ui.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.nothing2512.anticorona.repositories.CaseRepository
import com.github.nothing2512.anticorona.utils.launchMain

class HomeViewModel (private val caseRepository: CaseRepository): ViewModel() {

    private val _fragment = MutableLiveData<Fragment>()

    val fragment: LiveData<Fragment>
        get() = _fragment

    val globalCase = caseRepository.globalCase
    val indonesianCase = caseRepository.indonesianCase
    val provinceCase = caseRepository.provinceCase
    val countryCase = caseRepository.countriesCase

    fun getGlobalCase() {
        launchMain { caseRepository.getGlobalCase() }
    }

    fun getIndonesianCase() {
        launchMain { caseRepository.getIndonesianCase() }
    }

    fun getProvinceCase() {
        launchMain { caseRepository.getProvinceCase() }
    }

    fun getCountryCase() {
        launchMain { caseRepository.getCountriesCase() }
    }

    fun setFragment(f: Fragment) {
        if (_fragment.value != f) _fragment.postValue(f)
    }
}