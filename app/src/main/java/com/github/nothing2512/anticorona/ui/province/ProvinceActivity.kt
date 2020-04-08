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

package com.github.nothing2512.anticorona.ui.province

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse
import com.github.nothing2512.anticorona.databinding.ActivityProvinceBinding
import com.github.nothing2512.anticorona.parent.ParentActivity
import com.github.nothing2512.anticorona.ui.adapter.ProvinceAdapter
import com.github.nothing2512.anticorona.ui.dialog.ProvinceDialog
import com.github.nothing2512.anticorona.utils.Constants
import com.github.nothing2512.anticorona.vo.Status
import org.koin.android.ext.android.inject

/**
 * [ProvinceActivity] class
 * @author Robet Atiq Maulana Rifqi
 * @see ParentActivity
 */
class ProvinceActivity : ParentActivity<ActivityProvinceBinding>(R.layout.activity_province) {

    /**
     * Declaring variable
     *
     * @see ProvinceViewModel
     * @see inject
     * @see ProvinceLoading
     */
    private val provinceViewModel: ProvinceViewModel by inject()
    private lateinit var loading: ProvinceLoading

    /**
     * subscribing UI
     * @see ParentActivity.subscribeUI
     */
    override fun subscribeUI() {

        /**
         * Initialize Loading
         * @see ProvinceLoading
         */
        loading = ProvinceLoading(binding)

        /**
         * Getting data from intent
         * @see Intent.getParcelableArrayListExtra
         */
        val data: List<CaseResponse> = intent.getParcelableArrayListExtra(Constants.EXTRA_PROVINCE)

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
         * @see ProvinceViewModel.repoCase
         * @see observe
         */
        provinceViewModel.repoCase.observe {
            binding.provinceSwipe.isRefreshing = false
            when (it.status) {
                Status.LOADING -> loading.start()
                Status.ERROR -> {
                    serverDown()
                    binding.sfProvince.stopShimmer()
                }
                Status.SUCCESS -> {
                    provinceViewModel.setCases(it.data ?: listOf())
                    loading.stop()
                }
            }
        }

        /**
         * Observing country cases
         * @see ProvinceViewModel.cases
         * @see observe
         * @see RecyclerView.RecycledViewPool
         * @see LinearLayoutManager
         * @see ProvinceAdapter
         * @see ProvinceDialog
         */
        provinceViewModel.cases.observe {
            binding.rvProvince.apply {
                setHasFixedSize(true)
                setRecycledViewPool(RecyclerView.RecycledViewPool())
                layoutManager = LinearLayoutManager(this@ProvinceActivity)
                adapter = ProvinceAdapter(it) { item ->
                    ProvinceDialog.newInstance(item)
                        .show(supportFragmentManager, ProvinceDialog.TAG)
                }
            }
        }

        /**
         * Setting [SwipeRefreshLayout] function
         * @see SwipeRefreshLayout.setOnRefreshListener
         */
        binding.provinceSwipe.setOnRefreshListener { provinceViewModel.getCases() }

        /**
         * Set cases from intent data
         * @see ProvinceViewModel.setCases
         */
        provinceViewModel.setCases(data)
    }
}