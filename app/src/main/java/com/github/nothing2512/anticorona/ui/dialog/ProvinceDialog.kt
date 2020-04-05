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
import com.github.nothing2512.anticorona.databinding.DialogProvinceBinding
import com.github.nothing2512.anticorona.parent.ParentDialog

/**
 * [ProvinceDialog] class
 * @author Robet Atiq Maulana Rifqi
 * @constructor [item]
 * @see ParentDialog
 */
class ProvinceDialog(private val item: CaseResponse) :
    ParentDialog<DialogProvinceBinding>(R.layout.dialog_province) {

    /**
     * Subscribing ui
     * @see ParentDialog.subscribeUI
     */
    override fun subscribeUI() {
        binding.item = item
    }

    companion object {

        /**
         * Creating new instance of [ProvinceDialog]
         * @param item
         * @return
         */
        @JvmStatic
        fun newInstance(item: CaseResponse) = ProvinceDialog(item)

        /**
         * Declaring dialog tag
         */
        const val TAG = "ProvinceDialog"
    }
}