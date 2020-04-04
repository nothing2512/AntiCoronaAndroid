package com.github.nothing2512.anticorona.ui.faqs

import android.os.Bundle
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.databinding.FragmentFaqsBinding
import com.github.nothing2512.anticorona.parent.ParentFragment
import com.github.nothing2512.anticorona.ui.adapter.FaqsAdapter
import com.github.nothing2512.anticorona.ui.dialog.FaqsDialog
import com.github.nothing2512.anticorona.vo.Status
import org.koin.android.ext.android.inject

class FaqsFragment : ParentFragment<FragmentFaqsBinding>(R.layout.fragment_faqs) {

    private val faqsViewModel: FaqsViewModel by inject()
    private lateinit var faqsLoading: FaqsLoading

    override fun subscribeUI(bundle: Bundle?) {

        faqsLoading = FaqsLoading(binding)

        faqsViewModel.faqs.observe {
            when(it.status) {
                Status.ERROR -> serverDown()
                Status.LOADING -> faqsLoading.start()
                Status.SUCCESS -> {
                    faqsLoading.stop()
                    val adapter = FaqsAdapter(it.data ?: listOf()) { response ->
                        activity?.supportFragmentManager?.let { fm ->
                            FaqsDialog.newInstance(response)
                                .show(fm, FaqsDialog.TAG)
                        }
                    }
                }
            }
        }

        faqsViewModel.getFaqs()
    }

    override fun onRefresh() {
        faqsViewModel.getFaqs()
    }

    companion object {

        @JvmStatic
        fun newInstance() = FaqsFragment()
    }
}