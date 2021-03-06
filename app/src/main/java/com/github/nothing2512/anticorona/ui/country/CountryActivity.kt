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

package com.github.nothing2512.anticorona.ui.country

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse
import com.github.nothing2512.anticorona.databinding.ActivityCountryBinding
import com.github.nothing2512.anticorona.parent.ParentActivity
import com.github.nothing2512.anticorona.ui.adapter.CountryAdapter
import com.github.nothing2512.anticorona.ui.dialog.CountryDialog
import com.github.nothing2512.anticorona.utils.Constants
import com.github.nothing2512.anticorona.vo.Status
import org.koin.android.ext.android.inject

/**
 * [CountryActivity] class
 * @author Robet Atiq Maulana Rifqi
 * @see ParentActivity
 */
class CountryActivity : ParentActivity<ActivityCountryBinding>(R.layout.activity_country) {

    /**
     * Declaring variable
     *
     * @see CountryViewModel
     * @see inject
     * @see CountryLoading
     */
    private val countryViewModel: CountryViewModel by inject()
    private lateinit var loading: CountryLoading

    /**
     * subscribing UI
     * @see ParentActivity.subscribeUI
     */
    override fun subscribeUI() {

        /**
         * Initialize Loading
         * @see CountryLoading
         */
        loading = CountryLoading(binding)

        /**
         * Getting data from intent
         * @see Intent.getParcelableArrayListExtra
         */
        val data: List<CaseResponse> = intent.getParcelableArrayListExtra(Constants.EXTRA_COUNTRY)

        /**
         * showing back button in toolbar
         * @see ParentActivity.initializeBackButton
         */
        initializeBackButton()

        /**
         * Set Toolbar Title
         * @see setToolbarTitle
         */
        setToolbarTitle(R.string.toolbar_countries)

        /**
         * Observing remote repository cases
         * @see CountryViewModel.repoCases
         * @see observe
         */
        countryViewModel.repoCases.observe {
            binding.countrySwipe.isRefreshing = false
            when (it.status) {
                Status.LOADING -> loading.start()
                Status.ERROR -> {
                    serverDown()
                    binding.sfCountry.stopShimmer()
                }
                Status.SUCCESS -> {
                    countryViewModel.setCases(it.data ?: listOf())
                    loading.stop()
                }
            }
        }

        /**
         * Observing country cases
         * @see CountryViewModel.cases
         * @see observe
         */
        countryViewModel.cases.observe {

            /**
             * Set data to recycler view
             *
             * @see RecyclerView.RecycledViewPool
             * @see GridLayoutManager
             * @see CountryAdapter
             * @see CountryDialog
             */
            binding.rvCountry.apply {
                setHasFixedSize(true)
                setRecycledViewPool(RecyclerView.RecycledViewPool())
                layoutManager = GridLayoutManager(this@CountryActivity, 2)
                adapter = CountryAdapter(it) { item ->
                    CountryDialog.newInstance(item).show(supportFragmentManager, CountryDialog.TAG)
                }
            }
        }

        /**
         * Setting [SwipeRefreshLayout] function
         * @see SwipeRefreshLayout.setOnRefreshListener
         */
        binding.countrySwipe.setOnRefreshListener { countryViewModel.getCases() }

        /**
         * Set cases from intent data
         * @see CountryViewModel.setCases
         */
        countryViewModel.setCases(data)
    }
}