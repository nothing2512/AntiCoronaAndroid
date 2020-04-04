package com.github.nothing2512.anticorona.ui.dialog

import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.data.remote.response.FaqsResponse
import com.github.nothing2512.anticorona.databinding.DialogFaqsBinding
import com.github.nothing2512.anticorona.parent.ParentDialog

class FaqsDialog(private val faqs: FaqsResponse): ParentDialog<DialogFaqsBinding>(R.layout.dialog_faqs) {
    override fun subscribeUI() {

    }

    companion object {

        @JvmStatic
        fun newInstance(faqs: FaqsResponse) = FaqsDialog(faqs)

        const val TAG = "FaqsDialog"
    }
}