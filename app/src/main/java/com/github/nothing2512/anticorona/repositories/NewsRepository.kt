/*
 * Copyright 2020 Nothing
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

/**
 * [NewsRepository] class
 * @author Robet Atiq Maulana Rifqi
 * @constructor [appExecutors], [services], [newsDao]
 *
 * @see AppExecutors
 * @see Services
 * @see NewsDao
 */
class NewsRepository(
    private val appExecutors: AppExecutors,
    private val services: Services,
    private val newsDao: NewsDao
) {

    /**
     * Declaring private read only variable
     *
     * @see MutableLiveData
     * @see Resource
     * @see NewsResponse
     */
    private val _news = MutableLiveData<Resource<List<NewsResponse>>>()

    /**
     * Declaring public read only variable
     *
     * @see LiveData
     * @see Resource
     * @see NewsResponse
     */
    val news: LiveData<Resource<List<NewsResponse>>>
        get() = _news

    /**
     * Getting news data
     */
    suspend fun getNews() {

        /**
         * getting locale language
         * @see Locale.getDefault
         */
        val lang = when (Locale.getDefault().country) {
            "en_US" -> "eng"
            "en_UK" -> "eng"
            "UK" -> "eng"
            "en" -> "eng"
            "US" -> "eng"
            else -> "in"
        }

        /**
         * Bouncing Services
         * @see BoundService
         */
        object : BoundService<List<NewsResponse>>(appExecutors) {

            /**
             * Getting data from remote services
             * @see Services
             * @see idle
             * @return
             */
            override fun createCall() = idle { services.getNews(lang) }

            /**
             * Getting data from in app database
             * @see NewsDao.getNews
             * @return
             */
            override fun loadFromDb(): LiveData<List<NewsResponse>> {
                val data = MutableLiveData<List<NewsResponse>>()
                newsDao.getNews()?.observeForever {
                    val responses = ArrayList<NewsResponse>()
                    it.forEach { item -> responses.add(item.toResponse()) }
                    data.postValue(responses)
                }
                return data
            }

            /**
             * Saving response from remote services to in app database
             * @param item
             * @see NewsDao.insert
             */
            override fun saveCallResult(item: List<NewsResponse>) {
                item.forEach { newsDao.insert(NewsEntity.parse(it)) }
            }
        }.asLiveData().observeForever {
            if (it.status == Status.SUCCESS) {
                if (_news.value == null) _news.postValue(it)
                if (_news.value != it) _news.postValue(it)
            } else if (it.status == Status.ERROR && _news.value == null) _news.postValue(it)
            else _news.postValue(it)
        }
    }
}