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

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.component.CurvedNavigation
import com.github.nothing2512.anticorona.databinding.ActivityHomeBinding
import com.github.nothing2512.anticorona.parent.ParentActivity
import com.github.nothing2512.anticorona.parent.ParentFragment
import com.github.nothing2512.anticorona.utils.Constants
import com.github.nothing2512.anticorona.utils.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * [HomeActivity] class
 * @author Robet Atiq Maulana Rifqi
 * @see ParentActivity
 */
class HomeActivity : ParentActivity<ActivityHomeBinding>(R.layout.activity_home) {

    /**
     * Declaring private variable
     *
     * @see HomeViewModel
     * @see viewModel
     */
    private val homeViewModel: HomeViewModel by viewModel()
    private var isHome = true

    /**
     * subscribing ui
     * @see ParentActivity.subscribeUI
     */
    override fun subscribeUI() {

        /**
         * set refresh listener for [SwipeRefreshLayout]
         * @see SwipeRefreshLayout.setOnRefreshListener
         */
        binding.homeSwipe.setOnRefreshListener {
            (homeViewModel.fragment.value as ParentFragment<*>).refresh()
            binding.homeSwipe.isRefreshing = false
        }

        /**
         * Bind [ActivityHomeBinding] data
         */
        binding.lifecycleOwner = this
        binding.activity = this
        binding.viewModel = homeViewModel

        /**
         * Setup bottom naviagation
         * @see CurvedNavigation.setup
         */
        binding.homeBottomNav.setup(deviceWidth, binding.lin)

        /**
         * Setting [CurvedNavigation] selected item listener
         * @see CurvedNavigation.setOnNavigationItemSelectedListener
         */
        binding.homeBottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.btNavFaq -> {

                    /**
                     * setting FAQ fragment
                     *
                     * @see setToolbarTitle
                     * @see HomeViewModel.setFragment
                     * @see Constants.FRAGMENT_FAQ
                     * @see CurvedNavigation.update
                     * @see bind
                     */
                    setToolbarTitle(R.string.toolbar_faq)
                    homeViewModel.setFragment(Constants.FRAGMENT_FAQ)
                    binding.homeBottomNav.update(binding.homeButtonNav, Constants.FAQS)
                    binding.homeButtonNav.bind(R.drawable.faq_white)
                    isHome = false
                }
                R.id.btNavHome -> {

                    /**
                     * setting Home fragment
                     *
                     * @see setToolbarTitle
                     * @see HomeViewModel.setFragment
                     * @see Constants.FRAGMENT_HOME
                     * @see CurvedNavigation.update
                     * @see bind
                     */
                    setToolbarTitle(R.string.toolbar_cases)
                    homeViewModel.setFragment(Constants.FRAGMENT_HOME)
                    binding.homeBottomNav.update(binding.homeButtonNav, Constants.HOME)
                    binding.homeButtonNav.bind(R.drawable.home_white)
                    isHome = true
                }
                R.id.btNavNews -> {

                    /**
                     * setting News fragment
                     *
                     * @see setToolbarTitle
                     * @see HomeViewModel.setFragment
                     * @see Constants.FRAGMENT_NEWS
                     * @see CurvedNavigation.update
                     * @see bind
                     */
                    setToolbarTitle(R.string.toolbar_news)
                    homeViewModel.setFragment(Constants.FRAGMENT_NEWS)
                    binding.homeBottomNav.update(binding.homeButtonNav, Constants.NEWS)
                    binding.homeButtonNav.bind(R.drawable.news_white)
                    isHome = false
                }
            }
            true
        }

        /**
         * Set default [CurvedNavigation] selected item to [HomeFragment]
         */
        binding.homeBottomNav.selectedItemId = R.id.btNavHome
    }

    /**
     * setup back button pressed
     * if fragment is not [HomeFragment] then set fragment to [HomeFragment]
     * if fragment is [HomeFragment] finishing activity
     */
    override fun onBackPressed() {
        if (isHome) super.onBackPressed()
        else binding.homeBottomNav.selectedItemId = R.id.btNavHome
    }

}