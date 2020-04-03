package com.github.nothing2512.anticorona.ui.dialog

import android.app.Activity
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.databinding.DialogThemeBinding
import com.github.nothing2512.anticorona.parent.ParentDialog
import com.github.nothing2512.anticorona.vo.Theme

class ThemeDialog(private val activity: Activity) :
    ParentDialog<DialogThemeBinding>(R.layout.dialog_theme) {

    override fun subscribeUI() {
        when (preference.theme) {
            Theme.DEFAULT.toString() -> binding.rbThemeDefault.isChecked = true
            Theme.OCEAN.toString() -> binding.rbThemeOcean.isChecked = true
            else -> binding.rbThemeDefault.isChecked = true
        }

        binding.rbThemeOcean.setOnClickListener {
            preference.setTheme(Theme.OCEAN)
            dismiss()
            activity.recreate()
        }

        binding.rbThemeDefault.setOnClickListener {
            preference.setTheme(Theme.DEFAULT)
            dismiss()
            activity.recreate()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(activity: Activity) = ThemeDialog(activity)

        const val TAG = "ThemeDialog"
    }
}