package com.github.nothing2512.anticorona.ui.province

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse
import com.github.nothing2512.anticorona.repositories.CaseRepository
import com.github.nothing2512.anticorona.utils.launchMain

class ProvinceViewModel(private val caseRepository: CaseRepository) : ViewModel() {

    val repoCase = caseRepository.provinceCase

    private val _cases = MutableLiveData<List<CaseResponse>>()
    val cases: LiveData<List<CaseResponse>>
        get() = _cases

    fun getCases() {
        launchMain { caseRepository.getProvinceCase() }
    }

    fun setCases(data: List<CaseResponse>) {
        if(data != _cases.value) _cases.postValue(data)
    }
}