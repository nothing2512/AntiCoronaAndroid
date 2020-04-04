package com.github.nothing2512.anticorona.ui.dialog

import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse
import com.github.nothing2512.anticorona.databinding.DialogCountryBinding
import com.github.nothing2512.anticorona.parent.ParentDialog

class CountryDialog(private val item: CaseResponse) :
    ParentDialog<DialogCountryBinding>(R.layout.dialog_country) {

    override fun subscribeUI() {
        binding.item = item
    }

    companion object {

        @JvmStatic
        fun newInstance(item: CaseResponse) = CountryDialog(item)

        const val TAG = "CountryDialog"
    }
}