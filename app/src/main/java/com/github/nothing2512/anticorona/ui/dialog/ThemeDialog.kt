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

import android.app.Activity
import android.widget.RadioButton
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.databinding.DialogThemeBinding
import com.github.nothing2512.anticorona.parent.ParentDialog
import com.github.nothing2512.anticorona.utils.Preference
import com.github.nothing2512.anticorona.vo.Theme

/**
 * [ThemeDialog] class
 * @author Robet Atiq Maulana Rifqi
 * @constructor [activity]
 * @see ParentDialog
 */
class ThemeDialog(private val activity: Activity) :
    ParentDialog<DialogThemeBinding>(R.layout.dialog_theme) {

    /**
     * Subscribing ui
     * @see ParentDialog.subscribeUI
     */
    override fun subscribeUI() {

        /**
         * get theme data from [Preference]
         * Set checked [RadioButton]
         *
         * @see Preference.theme
         * @see RadioButton.setChecked
         */
        when (preference.theme) {
            Theme.DEFAULT.toString() -> binding.rbThemeDefault.isChecked = true
            Theme.OCEAN.toString() -> binding.rbThemeOcean.isChecked = true
            else -> binding.rbThemeDefault.isChecked = true
        }

        /**
         * Handling Ocean theme picked
         *
         * @see RadioButton.setOnClickListener
         * @see Preference.setTheme
         * @see dismiss
         */
        binding.rbThemeOcean.setOnClickListener {
            preference.setTheme(Theme.OCEAN)
            dismiss()
            activity.apply {
                finish()
                startActivity(intent)
            }
        }

        /**
         * Handling Default theme picked
         *
         * @see RadioButton.setOnClickListener
         * @see Preference.setTheme
         * @see dismiss
         */
        binding.rbThemeDefault.setOnClickListener {
            preference.setTheme(Theme.DEFAULT)
            dismiss()
            activity.apply {
                finish()
                startActivity(intent)
            }
        }
    }

    companion object {

        /**
         * Creating new instance of [ThemeDialog]
         * @param activity
         * @return
         */
        @JvmStatic
        fun newInstance(activity: Activity) = ThemeDialog(activity)

        /**
         * Declaring dialog tag
         */
        const val TAG = "ThemeDialog"
    }
}