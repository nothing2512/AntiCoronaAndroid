package com.github.nothing2512.anticorona.ui.province

import androidx.lifecycle.ViewModel
import com.github.nothing2512.anticorona.repositories.CaseRepository
import com.github.nothing2512.anticorona.utils.launchMain

class ProvinceViewModel(private val caseRepository: CaseRepository) : ViewModel() {

    val cases = caseRepository.provinceCase

    fun getCases() {
        launchMain { caseRepository.getProvinceCase() }
    }
}