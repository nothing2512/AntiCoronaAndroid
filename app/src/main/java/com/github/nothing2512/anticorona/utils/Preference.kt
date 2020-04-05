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

import android.content.Context
import android.content.SharedPreferences
import com.github.nothing2512.anticorona.vo.Theme

/**
 * [Preference] class
 * @author Robet Atiq Maulana rifqi
 *
 * @constructor [context]
 *
 * @see Context
 */
@Suppress("unused")
class Preference(private val context: Context) {

    /**
     * Initializing access of SharedPReference
     *
     * @see Context.getSharedPreferences
     * @see Constants.PREF_NAME
     * @see Constants.PREF_MODE
     */
    private val access = context.getSharedPreferences(Constants.PREF_NAME, Constants.PREF_MODE)

    /**
     * Initializing SharedPReference Editor
     * @see SharedPreferences.edit
     */
    private val editor = access.edit()

    /**
     * Get Theme key
     */
    val theme: String
        get() = getString(Constants.THEME_KEY) ?: ""

    /**
     * function set
     * Setting SharedPreference value
     *
     * @param key
     * @param value
     */
    fun set(key: String, value: Any) {
        editor.apply {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                is String -> putString(key, value)
            }
            apply()
        }
    }

    /**
     * function getBoolean
     * getting Boolean data
     *
     * @param key
     *
     * @return Boolean
     */
    fun getBoolean(key: String) = access.getBoolean(key, false)

    /**
     * function getInt
     * getting Int data
     *
     * @param key
     *
     * @return Int
     */
    fun getInt(key: String) = access.getInt(key, 0)

    /**
     * function getFloat
     * getting Float data
     *
     * @param key
     *
     * @return Float
     */
    fun getFloat(key: String) = access.getFloat(key, 0f)

    /**
     * function getLong
     * getting Long data
     *
     * @param key
     *
     * @return Long
     */
    @Suppress("MemberVisibilityCanBePrivate")
    fun getLong(key: String) = access.getLong(key, 0L)

    /**
     * function getString
     * getting String data
     *
     * @param key
     *
     * @return String
     */
    @Suppress("HasPlatformType")
    fun getString(key: String) = access.getString(key, "")

    /**
     * function setTheme
     * setting app theme
     *
     * @param theme
     */
    fun setTheme(theme: Theme) {
        set(Constants.THEME_KEY, theme.toString())
    }
}