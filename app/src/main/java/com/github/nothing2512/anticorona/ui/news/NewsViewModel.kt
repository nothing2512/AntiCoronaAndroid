package com.github.nothing2512.anticorona.ui.news

import androidx.lifecycle.ViewModel
import com.github.nothing2512.anticorona.repositories.NewsRepository
import com.github.nothing2512.anticorona.utils.launchMain

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    val news = newsRepository.news

    fun getNews() {
        launchMain { newsRepository.getNews() }
    }
}