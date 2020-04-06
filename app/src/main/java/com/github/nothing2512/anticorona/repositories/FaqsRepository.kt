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
import com.github.nothing2512.anticorona.data.local.dao.FaqsDao
import com.github.nothing2512.anticorona.data.local.entity.FaqsEntity
import com.github.nothing2512.anticorona.data.remote.Services
import com.github.nothing2512.anticorona.data.remote.response.FaqsResponse
import com.github.nothing2512.anticorona.utils.AppExecutors
import com.github.nothing2512.anticorona.utils.BoundService
import com.github.nothing2512.anticorona.utils.Constants
import com.github.nothing2512.anticorona.utils.idle
import com.github.nothing2512.anticorona.vo.Resource
import com.github.nothing2512.anticorona.vo.Status
import java.util.*
import kotlin.collections.ArrayList

/**
 * [FaqsRepository] class
 * @author Robet Atiq Maulana Rifqi
 * @constructor [appExecutors], [services], [faqsDao]
 */
class FaqsRepository(
    private val appExecutors: AppExecutors,
    private val services: Services,
    private val faqsDao: FaqsDao
) {

    /**
     * Declaring private read only variable
     *
     * @see MutableLiveData
     * @see Resource
     * @see FaqsResponse
     */
    private val _faqs = MutableLiveData<Resource<List<FaqsResponse>>>()

    /**
     * Declaring public read only variable
     *
     * @see LiveData
     * @see Resource
     * @see FaqsResponse
     */
    val faqs: LiveData<Resource<List<FaqsResponse>>>
        get() = _faqs

    /**
     * Getting faqs data
     */
    suspend fun getFaqs() {

        /**
         * Getting locale language
         * @see Locale.getDefault
         */
        val lang = if (Locale.getDefault().country == Constants.LANG_ID) "in" else "eng"

        /**
         * Bouncing services
         * @see BoundService
         */
        object : BoundService<List<FaqsResponse>>(appExecutors) {

            /**
             * Getting data from remote repository
             * @see Services
             * @see idle
             * @return
             */
            override fun createCall() = idle { services.getFaqs(lang) }

            /**
             * Getting data from in app database
             * @see FaqsDao.getFaqs
             * @return
             */
            override fun loadFromDb(): LiveData<List<FaqsResponse>> {
                val data = MutableLiveData<List<FaqsResponse>>()
                faqsDao.getFaqs()?.observeForever {
                    val responses = ArrayList<FaqsResponse>()
                    it.forEach { item -> responses.add(item.toResponse()) }
                    data.postValue(responses)
                }
                return data
            }

            /**
             * Saving response from remote repository to in app database
             * @param item
             * @see FaqsDao.insert
             */
            override fun saveCallResult(item: List<FaqsResponse>) {
                item.forEach {
                    faqsDao.insert(FaqsEntity.parse(it))
                }
            }
        }.asLiveData().observeForever {
            if (it.status == Status.SUCCESS) {
                if (faqs.value == null) _faqs.postValue(it)
                if (faqs.value != it) _faqs.postValue(it)
            } else if (it.status == Status.ERROR && faqs.value == null) _faqs.postValue(it)
            else _faqs.postValue(it)
        }
    }
}