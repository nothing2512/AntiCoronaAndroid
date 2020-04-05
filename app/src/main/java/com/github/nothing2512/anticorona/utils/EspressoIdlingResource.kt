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

package com.github.nothing2512.anticorona.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.idling.CountingIdlingResource

/**
 * [EspressoIdlingResource] object
 * @author Robet Atiq Mualana Rifqi
 */
object EspressoIdlingResource {

    /**
     * idling count variable
     *
     * @see CountingIdlingResource
     * @see Constants.IDLING
     */
    private val count = CountingIdlingResource(Constants.IDLING)

    /**
     * function handle
     * using to handle fetch data from remote data source
     *
     * @param onHandle
     *
     * @return LiveData
     */
    fun <T> handle(onHandle: () -> LiveData<T>): LiveData<T> {

        val liveData = MutableLiveData<T>()

        /**
         * Incrementing count
         * @see CountingIdlingResource.increment
         */
        count.increment()

        /**
         * Starting idling resource
         * @see postDelay
         */
        postDelay {
            onHandle.invoke().observeForever { liveData.postValue(it) }
            if (!count.isIdleNow) count.decrement()
        }

        /**
         * return
         */
        return liveData
    }
}