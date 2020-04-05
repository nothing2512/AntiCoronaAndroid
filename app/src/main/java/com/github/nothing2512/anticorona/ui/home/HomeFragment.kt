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

package com.github.nothing2512.anticorona.ui.home

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.databinding.FragmentHomeBinding
import com.github.nothing2512.anticorona.parent.ParentFragment
import com.github.nothing2512.anticorona.ui.adapter.CountryAdapter
import com.github.nothing2512.anticorona.ui.adapter.ProvinceAdapter
import com.github.nothing2512.anticorona.ui.country.CountryActivity
import com.github.nothing2512.anticorona.ui.dialog.CountryDialog
import com.github.nothing2512.anticorona.ui.dialog.ProvinceDialog
import com.github.nothing2512.anticorona.ui.province.ProvinceActivity
import com.github.nothing2512.anticorona.utils.*
import com.github.nothing2512.anticorona.vo.Status
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * [HomeFragment] class
 * @author Robet Atiq Maulana Rifqi
 * @see ParentFragment
 */
class HomeFragment : ParentFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    /**
     * Declaring private variable
     * @see HomeViewModel
     * @see sharedViewModel
     * @see HomeLoading
     */
    private val homeViewModel: HomeViewModel by sharedViewModel()
    private lateinit var loading: HomeLoading
    private var isFinishFetch = false

    /**
     * subscribing ui
     * @see ParentFragment.subscribeUI
     * @param bundle
     */
    override fun subscribeUI(bundle: Bundle?) {

        /**
         * Initialize [HomeLoading]
         */
        loading = HomeLoading(binding)

        /**
         * observing data
         *
         * @see observeHome
         * @see observeProvince
         * @see observeGlobal
         * @see observeCountry
         */
        observeHome()
        observeProvince()
        observeGlobal()
        observeCountry()

        /**
         * Disabling Seekbar
         */
        binding.sbHomeDeath.isEnabled = false
        binding.sbHomeRecovered.isEnabled = false

        /**
         * Get indonesian corona cases data
         * @see HomeViewModel.getIndonesianCase
         */
        homeViewModel.getIndonesianCase()
    }

    /**
     * observing indonesian data
     */
    private fun observeHome() {
        homeViewModel.indonesianCase.observe {
            when (it.status) {
                Status.LOADING -> loading.startAll()
                Status.ERROR -> if (!isFinishFetch) {
                    serverDown()
                    isFinishFetch = true
                }
                Status.SUCCESS -> {
                    loading.indonesianStop()
                    binding.indonesian = it.data
                    homeViewModel.getProvinceCase()
                }
            }
        }
    }

    /**
     * observing province data
     */
    private fun observeProvince() {
        homeViewModel.provinceCase.observe {
            when (it.status) {
                Status.LOADING -> loading.provinceStart()
                Status.ERROR -> serverDown()
                Status.SUCCESS -> {

                    /**
                     * Setting up province recycler view
                     * @see RecyclerView.setNestedScrollingEnabled
                     * @see RecyclerView.setAdapter
                     * @see RecyclerView.setLayoutManager
                     */
                    binding.rvHomeProvince.apply {
                        isNestedScrollingEnabled = true
                        layoutManager = LinearLayoutManager(activity?.applicationContext)
                        adapter = ProvinceAdapter(it.data?.slice(0..2) ?: listOf()) { item ->
                            activity?.supportFragmentManager?.let { fm ->
                                ProvinceDialog.newInstance(item)
                                    .show(fm, ProvinceDialog.TAG)
                            }
                        }
                        clearFocus()
                    }

                    /**
                     * set show more province text on click listener
                     * @see goto
                     */
                    binding.tvHomeProvinceHighlight.setOnClickListener { _ ->
                        goto(ProvinceActivity::class.java) {
                            putParcelableArrayListExtra(
                                Constants.EXTRA_PROVINCE,
                                it.data?.toArrayList()
                            )
                        }
                    }

                    loading.provinceStop()
                    homeViewModel.getGlobalCase()
                }
            }
        }
    }

    /**
     * observing global data
     */
    private fun observeGlobal() {
        homeViewModel.globalCase.observe {
            when (it.status) {
                Status.LOADING -> loading.globalStart()
                Status.ERROR -> serverDown()
                Status.SUCCESS -> {

                    val recovered = it.data?.cases?.div(it.data.recovered)
                    val death = it.data?.cases?.div(it.data.death)

                    /**
                     * setting and animating global data
                     * @see animate
                     */
                    binding.global = it.data
                    binding.sbHomeRecovered.animate(recovered ?: 0)
                    binding.sbHomeDeath.animate(death ?: 0)

                    loading.globalStop()
                    homeViewModel.getCountryCase()
                }
            }
        }
    }

    /**
     * observing country data
     */
    private fun observeCountry() {
        homeViewModel.countryCase.observe {
            when (it.status) {
                Status.LOADING -> loading.countryStart()
                Status.ERROR -> serverDown()
                Status.SUCCESS -> {

                    /**
                     * Setting up province recycler view
                     * @see RecyclerView.setNestedScrollingEnabled
                     * @see RecyclerView.setAdapter
                     * @see RecyclerView.setLayoutManager
                     */
                    binding.rvHomeGlobal.apply {
                        isNestedScrollingEnabled = true
                        layoutManager = GridLayoutManager(activity?.applicationContext, 2)
                        adapter = CountryAdapter(it.data?.slice(0..5) ?: listOf()) { item ->
                            activity?.supportFragmentManager?.let { fm ->
                                CountryDialog.newInstance(item)
                                    .show(fm, CountryDialog.TAG)
                            }
                        }
                        clearFocus()
                    }

                    /**
                     * set show more province text on click listener
                     * @see goto
                     */
                    binding.tvHomeGlobalHighlight.setOnClickListener { _ ->
                        goto(CountryActivity::class.java) {
                            putParcelableArrayListExtra(
                                Constants.EXTRA_COUNTRY,
                                it.data?.toArrayList()
                            )
                        }
                    }

                    loading.countryStop()
                }
            }
        }
    }

    /**
     * triggered function when activity is refreshed
     * @see ParentFragment.onRefresh
     * @see HomeViewModel.getIndonesianCase
     */
    override fun onRefresh() {
        isFinishFetch = false
        homeViewModel.getIndonesianCase()
    }

    companion object {

        /**
         * Creating new instance of [HomeFragment]
         * @return
         */
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}