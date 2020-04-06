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

package com.github.nothing2512.anticorona.ui.country

import com.github.nothing2512.anticorona.databinding.ActivityCountryBinding
import com.github.nothing2512.anticorona.parent.ParentLoading

/**
 * [CountryLoading] class
 * @author Robet Atiq Maulana Rifqi
 * @constructor [binding]
 * @see ParentLoading
 */
class CountryLoading(private val binding: ActivityCountryBinding) : ParentLoading() {

    /**
     * start loading
     * @see ParentLoading.start
     */
    fun start() {
        binding.apply { start(sfCountry, rvCountry) }
    }

    /**
     * stop loading
     * @see ParentLoading.stop
     */
    fun stop() {
        binding.apply { stop(sfCountry, rvCountry) }
    }
}