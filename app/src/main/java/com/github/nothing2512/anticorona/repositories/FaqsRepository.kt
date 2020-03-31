package com.github.nothing2512.anticorona.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.nothing2512.anticorona.data.ApiResponse
import com.github.nothing2512.anticorona.data.Services
import com.github.nothing2512.anticorona.data.response.FaqsResponse
import com.github.nothing2512.anticorona.utils.AppExecutors
import com.github.nothing2512.anticorona.utils.BoundService
import com.github.nothing2512.anticorona.utils.idle
import com.github.nothing2512.anticorona.vo.Resource
import java.util.*

class FaqsRepository (
    private val appExecutors: AppExecutors,
    private val services: Services
) {

    private val _faqs = MutableLiveData<Resource<List<FaqsResponse>>>()

    val faqs: LiveData<Resource<List<FaqsResponse>>>
        get() = _faqs

    suspend fun getFaqs() {
        val lang = when(Locale.getDefault().country) {
            "en_US" -> "eng"
            "en_UK" -> "eng"
            "UK" -> "eng"
            "en" -> "eng"
            "US" -> "eng"
            else -> "in"
        }
        object: BoundService<List<FaqsResponse>>(appExecutors) {
            override fun createCall() = idle { services.getFaqs(lang) }
        }.asLiveData().observeForever { _faqs.postValue(it) }
    }
}