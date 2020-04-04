package com.github.nothing2512.anticorona.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.nothing2512.anticorona.data.local.dao.FaqsDao
import com.github.nothing2512.anticorona.data.local.entity.FaqsEntity
import com.github.nothing2512.anticorona.data.remote.Services
import com.github.nothing2512.anticorona.data.remote.response.FaqsResponse
import com.github.nothing2512.anticorona.utils.AppExecutors
import com.github.nothing2512.anticorona.utils.BoundService
import com.github.nothing2512.anticorona.utils.idle
import com.github.nothing2512.anticorona.vo.Resource
import com.github.nothing2512.anticorona.vo.Status
import java.util.*
import kotlin.collections.ArrayList

class FaqsRepository(
    private val appExecutors: AppExecutors,
    private val services: Services,
    private val faqsDao: FaqsDao
) {

    private val _faqs = MutableLiveData<Resource<List<FaqsResponse>>>()

    val faqs: LiveData<Resource<List<FaqsResponse>>>
        get() = _faqs

    suspend fun getFaqs() {

        val lang = when (Locale.getDefault().country) {
            "en_US" -> "eng"
            "en_UK" -> "eng"
            "UK" -> "eng"
            "en" -> "eng"
            "US" -> "eng"
            else -> "in"
        }

        object : BoundService<List<FaqsResponse>>(appExecutors) {

            override fun createCall() = idle { services.getFaqs(lang) }

            override fun loadFromDb(): LiveData<List<FaqsResponse>> {
                val data = MutableLiveData<List<FaqsResponse>>()
                faqsDao.getFaqs()?.observeForever {
                    val responses = ArrayList<FaqsResponse>()
                    it.forEach { item -> responses.add(item.toResponse()) }
                    data.postValue(responses)
                }
                return data
            }

            override fun saveCallResult(item: List<FaqsResponse>) {
                faqsDao.delete()
                item.forEach {
                    faqsDao.insert(FaqsEntity.parse(it))
                }
            }
        }.asLiveData().observeForever {
            if(it.status == Status.SUCCESS) {
                if (faqs.value == null ) _faqs.postValue(it)
                if (faqs.value != it) _faqs.postValue(it)
            } else if(it.status == Status.ERROR && faqs.value == null) _faqs.postValue(it)
            else _faqs.postValue(it)
        }
    }
}