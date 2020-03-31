package com.github.nothing2512.anticorona.ui.dialog

import android.app.Activity
import android.view.ViewGroup
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.data.response.CaseResponse
import com.github.nothing2512.anticorona.databinding.DialogProvinceBinding
import com.github.nothing2512.anticorona.parent.ParentDialog

class ProvinceDialog(
    activity: Activity,
    parent: ViewGroup,
    item: CaseResponse
) : ParentDialog<DialogProvinceBinding>(activity, parent, R.layout.dialog_province) {

    override fun subscribeUI() {

    }
}