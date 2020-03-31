package com.github.nothing2512.anticorona.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {

    private val count = CountingIdlingResource(Constants.IDLING)

    fun <T> handle(onHandle: () -> LiveData<T>): LiveData<T> {

        val liveData = MutableLiveData<T>()

        count.increment()
        postDelay {
            onHandle.invoke().observeForever { liveData.postValue(it) }
            if (!count.isIdleNow) count.decrement()
        }

        return liveData
    }
}