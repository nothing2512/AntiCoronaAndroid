package com.github.nothing2512.anticorona.ui.country

import androidx.lifecycle.ViewModel
import com.github.nothing2512.anticorona.repositories.CaseRepository
import com.github.nothing2512.anticorona.utils.launchMain

class CountryViewModel(private val caseRepository: CaseRepository): ViewModel() {

    val cases = caseRepository.countriesCase

    fun getCases() {
        launchMain { caseRepository.getCountriesCase() }
    }
}