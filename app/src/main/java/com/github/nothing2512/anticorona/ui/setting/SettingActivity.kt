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

package com.github.nothing2512.anticorona.ui.setting

import android.app.AlarmManager
import android.content.Intent
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.databinding.ActivitySettingBinding
import com.github.nothing2512.anticorona.parent.ParentActivity
import com.github.nothing2512.anticorona.services.NotificationReceiver
import com.github.nothing2512.anticorona.ui.dialog.ContactDialog
import com.github.nothing2512.anticorona.ui.dialog.ThemeDialog

/**
 * [SettingActivity] class
 * @author Robet Atiq Maulana Rifqi
 * @see ParentActivity
 */
class SettingActivity : ParentActivity<ActivitySettingBinding>(R.layout.activity_setting) {

    override fun subscribeUI() {

        /**
         * Set toolbar title
         * @see ParentActivity.setToolbarTitle
         */
        setToolbarTitle(R.string.setting)

        /**
         * initialize back button in toolbar
         * @see ParentActivity.initializeBackButton
         */
        initializeBackButton()

        /**
         * set notification switch check or not
         * @see NotificationReceiver
         */
        binding.btNotification.isChecked = NotificationReceiver.isActive(applicationContext)

        /**
         * Changing language
         * open language setting
         *
         * @see Intent
         * @see Settings.ACTION_LOCALE_SETTINGS
         * @see AppCompatActivity.startActivity
         */
        binding.btChangeLanguage.setOnClickListener { startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS)) }

        /**
         * Changing Theme
         * open dialog to pick selected theme
         *
         * @see ThemeDialog
         */
        binding.btChangeTheme.setOnClickListener {
            ThemeDialog.newInstance(this).show(supportFragmentManager, ThemeDialog.TAG)
        }

        /**
         * Enabling notification
         * @see NotificationReceiver
         * @see AlarmManager
         */
        binding.btNotification.setOnClickListener {

            /**
             * Start notification service if switch on
             * cancel notification service if  switch off
             * @see NotificationReceiver
             */
            NotificationReceiver.apply {
                if (isActive(applicationContext)) cancel(applicationContext)
                else start(applicationContext)
            }
        }

        /**
         * Show Contact Dialog
         * @see ContactDialog
         */
        binding.btContact.setOnClickListener {
            ContactDialog.newInstance().show(supportFragmentManager, ContactDialog.TAG)
        }
    }
}