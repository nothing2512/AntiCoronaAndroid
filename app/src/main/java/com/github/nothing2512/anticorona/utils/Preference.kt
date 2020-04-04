package com.github.nothing2512.anticorona.utils

import android.content.Context
import com.github.nothing2512.anticorona.vo.Theme

@Suppress("unused")
class Preference(context: Context) {

    private val access = context.getSharedPreferences(Constants.PREF_NAME, Constants.PREF_MODE)
    private val editor = access.edit()

    val theme: String
        get() = getString(Constants.THEME_KEY) ?: ""

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

    fun getBoolean(key: String) = access.getBoolean(key, false)

    fun getInt(key: String) = access.getInt(key, 0)

    fun getFloat(key: String) = access.getFloat(key, 0f)

    fun getLong(key: String) = access.getLong(key, 0L)

    fun getString(key: String) = access.getString(key, "")

    fun setTheme(theme: Theme) {
        set(Constants.THEME_KEY, theme.toString())
    }
}