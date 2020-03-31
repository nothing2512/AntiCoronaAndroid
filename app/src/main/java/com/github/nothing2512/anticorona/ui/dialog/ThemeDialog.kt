package com.github.nothing2512.anticorona.ui.dialog

import android.app.Activity
import android.view.ViewGroup
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.databinding.DialogThemeBinding
import com.github.nothing2512.anticorona.parent.ParentDialog

class ThemeDialog(
    activity: Activity,
    parent: ViewGroup,
    onPick: (themeCode: Int) -> Unit
) : ParentDialog<DialogThemeBinding>(activity, parent, R.layout.dialog_theme) {

    override fun subscribeUI() {

    }
}