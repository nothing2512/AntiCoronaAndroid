package com.github.nothing2512.anticorona.ui.dialog

import android.app.Activity
import android.view.ViewGroup
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.data.response.CaseResponse
import com.github.nothing2512.anticorona.databinding.DialogCountryBinding
import com.github.nothing2512.anticorona.parent.ParentDialog

class CountryDialog(
    activity: Activity,
    parent: ViewGroup,
    item: CaseResponse
): ParentDialog<DialogCountryBinding>(activity, parent, R.layout.dialog_country) {

    override fun subscribeUI() {

    }
}