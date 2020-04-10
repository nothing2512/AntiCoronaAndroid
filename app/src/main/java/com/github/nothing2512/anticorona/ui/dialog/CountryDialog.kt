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

package com.github.nothing2512.anticorona.ui.dialog

import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse
import com.github.nothing2512.anticorona.databinding.DialogCountryBinding
import com.github.nothing2512.anticorona.parent.ParentDialog
import com.github.nothing2512.anticorona.utils.setup

/**
 * [CountryDialog] class
 * @author Robet Atiq Maulana Rifqi
 * @constructor [item]
 * @see ParentDialog
 */
class CountryDialog(private val item: CaseResponse) :
    ParentDialog<DialogCountryBinding>(R.layout.dialog_country) {

    /**
     * Subscribing ui
     * @see ParentDialog.subscribeUI
     */
    override fun subscribeUI() {
        binding.item = item
        binding.countryChart.setup(item.toEntry(context?.applicationContext))
    }

    companion object {

        /**
         * Creating new instance of [CountryDialog]
         * @param item
         * @return
         */
        @JvmStatic
        fun newInstance(item: CaseResponse) = CountryDialog(item)

        /**
         * Declaring dialog tag
         */
        const val TAG = "CountryDialog"
    }
}