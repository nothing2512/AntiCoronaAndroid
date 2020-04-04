package com.github.nothing2512.anticorona.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.nothing2512.anticorona.data.local.dao.NewsDao
import com.github.nothing2512.anticorona.data.local.entity.NewsEntity
import com.github.nothing2512.anticorona.data.remote.Services
import com.github.nothing2512.anticorona.data.remote.response.NewsResponse
import com.github.nothing2512.anticorona.utils.AppExecutors
import com.github.nothing2512.anticorona.utils.BoundService
import com.github.nothing2512.anticorona.utils.idle
import com.github.nothing2512.anticorona.vo.Resource
import com.github.nothing2512.anticorona.vo.Status
import java.util.*
import kotlin.collections.ArrayList

class NewsRepository(
    private val appExecutors: AppExecutors,
    private val services: Services,
    private val newsDao: NewsDao
) {

    private val _news = MutableLiveData<Resource<List<NewsResponse>>>()

    val news: LiveData<Resource<List<NewsResponse>>>
        get() = _news

    suspend fun getNews() {
        val lang = when (Locale.getDefault().country) {
            "en_US" -> "eng"
            "en_UK" -> "eng"
            "UK" -> "eng"
            "en" -> "eng"
            "US" -> "eng"
            else -> "in"
        }

        object : BoundService<List<NewsResponse>>(appExecutors) {

            override fun createCall() = idle { services.getNews(lang) }

            override fun loadFromDb(): LiveData<List<NewsResponse>> {
                val data = MutableLiveData<List<NewsResponse>>()
                newsDao.getNews()?.observeForever {
                    val responses = ArrayList<NewsResponse>()
                    it.forEach { item -> responses.add(item.toResponse()) }
                    data.postValue(responses)
                }
                return data
            }

            override fun saveCallResult(item: List<NewsResponse>) {
                newsDao.delete()
                item.forEach { newsDao.insert(NewsEntity.parse(it)) }
            }
        }.asLiveData().observeForever {
            if(it.status == Status.SUCCESS) {
                if(_news.value == null ) _news.postValue(it)
                if(_news.value != it) _news.postValue(it)
            } else if(it.status == Status.ERROR && _news.value == null) _news.postValue(it)
            else _news.postValue(it)
        }
    }
}