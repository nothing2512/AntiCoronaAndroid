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

import com.github.nothing2512.anticorona.ui.home.HomeActivity
import com.github.nothing2512.anticorona.ui.home.HomeViewModel

/**
 * [Constants] object
 * @author Robet Atiq Maulana Rifqi
 */
object Constants {

    /**
     * Idling configuration
     * @see EspressoIdlingResource
     */
    const val SERVICE_LATENCY_IN_MILLIS: Long = 2000
    const val IDLING = "GLOBAL"

    /**
     * SharedPreference configuration
     * @see Preference
     */
    const val PREF_NAME = "AnCorUtils"
    const val PREF_MODE = 0
    const val THEME_KEY = "ThemeKey"

    /**
     * fragment code
     * using to identify fragment position in BottomNavigation in HomeActivity
     *
     * @see HomeActivity.subscribeUI
     */
    const val FAQS = 1
    const val HOME = 2
    const val NEWS = 3

    /**
     * Default Font configuration
     */
    const val DEFAULT_FONT = "font/mitr_light.ttf"

    /**
     * fragment code
     * using to identify fragment position in HomeActivity
     *
     * @see HomeViewModel.setFragment
     */
    const val FRAGMENT_FAQ = 0
    const val FRAGMENT_HOME = 1
    const val FRAGMENT_NEWS = 2

    /**
     * Extra data key for intent
     */
    const val EXTRA_PROVINCE = "ExtraProvince"
    const val EXTRA_COUNTRY = "ExtraCountry"
    const val EXTRA_URL = "ExtraUrl"
    const val EXTRA_STATUS = "ExtraStatus"

    /**
     * TextView REG
     */
    const val TV_REG = "{{REG}}"

    /**
     * ID Lang code
     */
    const val LANG_ID = "ID"

    /**
     * Contact data
     */
    const val EXTRA_NUMBER = "jid"
    const val CS_WA = "6288234030448"
    const val CS_EMAIL = "blank345red@gmail.com"
    const val COVID_WA = "6281133399000"
    const val COVID_HOTLINE = "119"

    /**
     * Notification Data
     */
    const val NOTIF_NAME = "AnticoronaNotif"
    const val NOTIF_CHANNEL_ID = "AntiCoronaNotifID"
    const val NOTIF_ID = 2512
    const val NOTIF_REQ_CODE = 1206
}