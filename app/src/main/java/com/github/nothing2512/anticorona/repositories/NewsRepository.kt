package com.github.nothing2512.anticorona.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.nothing2512.anticorona.data.ApiResponse
import com.github.nothing2512.anticorona.data.Services
import com.github.nothing2512.anticorona.data.response.NewsResponse
import com.github.nothing2512.anticorona.utils.AppExecutors
import com.github.nothing2512.anticorona.utils.BoundService
import com.github.nothing2512.anticorona.utils.idle
import com.github.nothing2512.anticorona.vo.Resource
import java.util.*

class NewsRepository (
    private val appExecutors: AppExecutors,
    private val services: Services
) {

    private val _news = MutableLiveData<Resource<List<NewsResponse>>>()

    val news: LiveData<Resource<List<NewsResponse>>>
        get() = _news

    suspend fun getNews() {
        val lang = when(Locale.getDefault().country) {
            "en_US" -> "eng"
            "en_UK" -> "eng"
            "UK" -> "eng"
            "en" -> "eng"
            "US" -> "eng"
            else -> "in"
        }
        object : BoundService<List<NewsResponse>>(appExecutors) {
            override fun createCall() = idle { services.getNews(lang) }
        }.asLiveData().observeForever { _news.postValue(it) }
    }
}