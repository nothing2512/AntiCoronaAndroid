/*
 * Copyright 2020 Nothing
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nothing2512.anticorona.ui.faqs

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.databinding.FragmentFaqsBinding
import com.github.nothing2512.anticorona.parent.ParentFragment
import com.github.nothing2512.anticorona.ui.adapter.FaqsAdapter
import com.github.nothing2512.anticorona.ui.dialog.FaqsDialog
import com.github.nothing2512.anticorona.vo.Status
import org.koin.android.ext.android.inject

/**
 * [FaqsFragment] class
 * @author Robet Atiq Maulana Rifqi
 * @see ParentFragment
 */
class FaqsFragment : ParentFragment<FragmentFaqsBinding>(R.layout.fragment_faqs) {

    /**
     * Declaring private variable
     *
     * @see FaqsLoading
     * @see FaqsViewModel
     * @see inject
     */
    private val faqsViewModel: FaqsViewModel by inject()
    private lateinit var faqsLoading: FaqsLoading

    /**
     * Subscribing ui
     * @param bundle
     * @see ParentFragment.subscribeUI
     */
    override fun subscribeUI(bundle: Bundle?) {

        /**
         * initialize [FaqsLoading]
         */
        faqsLoading = FaqsLoading(binding)

        /**
         * observing faqs data
         * @see observe
         * @see FaqsViewModel.faqs
         */
        faqsViewModel.faqs.observe {
            when(it.status) {
                Status.ERROR -> {
                    serverDown()
                    binding.sfFaqs.stopShimmer()
                }
                Status.LOADING -> faqsLoading.start()
                Status.SUCCESS -> {
                    faqsLoading.stop()

                    /**
                     * Setup RecyclerView
                     *
                     * @see RecyclerView.RecycledViewPool
                     * @see LinearLayoutManager
                     * @see FaqsAdapter
                     * @see FaqsDialog
                     */
                    binding.rvFaqs.apply {
                        setHasFixedSize(true)
                        setRecycledViewPool(RecyclerView.RecycledViewPool())
                        layoutManager = LinearLayoutManager(activity?.applicationContext)
                        adapter = FaqsAdapter(it.data ?: listOf()) { response ->
                            activity?.supportFragmentManager?.let { fm ->
                                FaqsDialog.newInstance(response)
                                    .show(fm, FaqsDialog.TAG)
                            }
                        }
                    }
                }
            }
        }

        /**
         * Getting faqs data
         * @see FaqsViewModel.getFaqs
         */
        faqsViewModel.getFaqs()
    }

    /**
     * refreshing function when activity is swiped
     * @see ParentFragment.onRefresh
     * @see FaqsViewModel.getFaqs
     */
    override fun onRefresh() {
        faqsViewModel.getFaqs()
    }

    companion object {

        /**
         * Creating new instance of [FaqsFragment]
         * @return
         */
        @JvmStatic
        fun newInstance() = FaqsFragment()
    }
}