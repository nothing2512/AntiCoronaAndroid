package com.github.nothing2512.anticorona.ui.dialog

import android.app.Activity
import android.view.ViewGroup
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.databinding.DialogLanguageBinding
import com.github.nothing2512.anticorona.parent.ParentDialog

class LanguageDialog(onPick: (langCode: Int) -> Unit) : ParentDialog<DialogLanguageBinding>(R.layout.dialog_language) {

    override fun subscribeUI() {

    }
}