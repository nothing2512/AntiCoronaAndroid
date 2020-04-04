package com.github.nothing2512.anticorona.ui.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse
import com.github.nothing2512.anticorona.repositories.CaseRepository
import com.github.nothing2512.anticorona.utils.launchMain

class CountryViewModel(private val caseRepository: CaseRepository) : ViewModel() {

    val repoCases = caseRepository.countriesCase
    private val _cases = MutableLiveData<List<CaseResponse>>()
    val cases: LiveData<List<CaseResponse>>
        get() = _cases

    fun getCases() {
        launchMain { caseRepository.getCountriesCase() }
    }

    fun setCases(data: List<CaseResponse>) {
        if(_cases.value != data) _cases.postValue(data)
    }
}