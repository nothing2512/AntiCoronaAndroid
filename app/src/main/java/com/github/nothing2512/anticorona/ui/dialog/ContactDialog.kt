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
import com.github.nothing2512.anticorona.databinding.DialogContactBinding
import com.github.nothing2512.anticorona.parent.ParentDialog
import com.github.nothing2512.anticorona.utils.Constants
import com.github.nothing2512.anticorona.utils.openDial
import com.github.nothing2512.anticorona.utils.openEmail
import com.github.nothing2512.anticorona.utils.openWhatsapp

/**
 * [ContactDialog] class
 * @author Robet Atiq Maulana Rifqi
 * @see ParentDialog
 */
class ContactDialog : ParentDialog<DialogContactBinding>(R.layout.dialog_contact) {

    override fun subscribeUI() {

        /**
         * open COVID-19 information center whatsapp
         * @see openWhatsapp
         */
        binding.tvCovidWA.setOnClickListener { openWhatsapp(Constants.COVID_WA) }

        /**
         * open COVID-19 information center whatsapp
         * @see openWhatsapp
         */
        binding.imCovidWA.setOnClickListener { openWhatsapp(Constants.COVID_WA) }

        /**
         * open Anti Corona Support
         * @see openWhatsapp
         */
        binding.tvAntiCoronaWA.setOnClickListener { openWhatsapp(Constants.CS_WA) }

        /**
         * open Anti Corona Support
         * @see openWhatsapp
         */
        binding.imAntiCoronaWA.setOnClickListener { openWhatsapp(Constants.CS_WA) }

        /**
         * open COVID-19 Hotline
         * @see openDial
         */
        binding.tvHotline.setOnClickListener { openDial(Constants.COVID_HOTLINE) }

        /**
         * open COVID-19 Hotline
         * @see openDial
         */
        binding.imHotline.setOnClickListener { openDial(Constants.COVID_HOTLINE) }

        /**
         * open Anti corona email to report bug
         * @see openEmail
         */
        binding.tvReport.setOnClickListener { openEmail(Constants.CS_EMAIL) }

        /**
         * open Anti corona email to report bug
         * @see openEmail
         */
        binding.imReport.setOnClickListener { openEmail(Constants.CS_EMAIL) }
    }

    companion object {

        /**
         * Get [ContactDialog] instances
         */
        @JvmStatic
        fun newInstance() = ContactDialog()

        /**
         * Contact Dialog TAG
         */
        const val TAG = "ContactDialog"
    }
}