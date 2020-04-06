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

package com.github.nothing2512.anticorona.ui.news

import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.component.ConfiguratedWebView
import com.github.nothing2512.anticorona.databinding.ActivityNewsBinding
import com.github.nothing2512.anticorona.parent.ParentActivity
import com.github.nothing2512.anticorona.utils.Constants

/**
 * [NewsActivity] class
 * @author Robet Atiq Maulana Rifqi
 * @see ParentActivity
 */
class NewsActivity : ParentActivity<ActivityNewsBinding>(R.layout.activity_news) {

    /**
     * Subscribing ui
     * @see ParentActivity.subscribeUI
     */
    override fun subscribeUI() {

        /**
         * Set toolbar title
         * @see ParentActivity.setToolbarTitle
         */
        setToolbarTitle(R.string.toolbar_web)

        /**
         * Showing back button in toolbar
         * @see initializeBackButton
         */
        initializeBackButton()

        /**
         * Load url into web view
         * @see ConfiguratedWebView.load
         */
        val url = intent.getStringExtra(Constants.EXTRA_URL)
        binding.wvNews.load(url, binding.imNewsLoading)
    }
}