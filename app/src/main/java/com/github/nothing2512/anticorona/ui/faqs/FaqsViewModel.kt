package com.github.nothing2512.anticorona.ui.faqs

import androidx.lifecycle.ViewModel
import com.github.nothing2512.anticorona.repositories.FaqsRepository
import com.github.nothing2512.anticorona.utils.launchMain

class FaqsViewModel (private val faqsRepository: FaqsRepository): ViewModel() {

    val faqs = faqsRepository.faqs

    fun getFaqs() {
        launchMain { faqsRepository.getFaqs() }
    }
}