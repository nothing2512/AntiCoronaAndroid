package com.github.nothing2512.anticorona.ui.dialog

import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse
import com.github.nothing2512.anticorona.databinding.DialogProvinceBinding
import com.github.nothing2512.anticorona.parent.ParentDialog

class ProvinceDialog(private val item: CaseResponse) :
    ParentDialog<DialogProvinceBinding>(R.layout.dialog_province) {

    override fun subscribeUI() {
        binding.item = item
    }

    companion object {

        @JvmStatic
        fun newInstance(item: CaseResponse) = ProvinceDialog(item)

        const val TAG = "ProvinceDialog"
    }
}