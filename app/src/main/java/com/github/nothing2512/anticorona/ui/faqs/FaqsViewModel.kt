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

package com.github.nothing2512.anticorona.ui.faqs

import androidx.lifecycle.ViewModel
import com.github.nothing2512.anticorona.repositories.FaqsRepository
import com.github.nothing2512.anticorona.utils.launchMain

/**
 * [FaqsViewModel] class
 * @author Robet Atiq Maulana Rifqi
 * @constructor [faqsRepository]
 * @see FaqsRepository
 * @see ViewModel
 */
class FaqsViewModel(private val faqsRepository: FaqsRepository) : ViewModel() {

    /**
     * Declaring read only variable
     * @see FaqsRepository.faqs
     */
    val faqs = faqsRepository.faqs

    /**
     * Getting faqs data
     * using coroutine to execute code
     * @see launchMain
     * @see FaqsRepository.getFaqs
     */
    fun getFaqs() {
        launchMain { faqsRepository.getFaqs() }
    }
}