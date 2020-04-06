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

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.databinding.FragmentNewsBinding
import com.github.nothing2512.anticorona.parent.ParentFragment
import com.github.nothing2512.anticorona.ui.adapter.NewsAdapter
import com.github.nothing2512.anticorona.vo.Status
import org.koin.android.ext.android.inject

/**
 * [NewsFragment] class
 * @author Robet Atiq Maulana Rifqi
 * @see ParentFragment
 */
class NewsFragment : ParentFragment<FragmentNewsBinding>(R.layout.fragment_news) {

    /**
     * Declaring private read only variable
     *
     * @see NewsViewModel
     * @see inject
     * @see NewsLoading
     */
    private val newsViewModel: NewsViewModel by inject()
    private lateinit var newsLoading: NewsLoading

    /**
     * subscribing UI
     * @param bundle
     * @see ParentFragment.subscribeUI
     */
    override fun subscribeUI(bundle: Bundle?) {

        /**
         * Initializing [NewsLoading]
         */
        newsLoading = NewsLoading(binding)

        /**
         * observing news data
         * @see observe
         * @see NewsViewModel.news
         */
        newsViewModel.news.observe {
            when (it.status) {
                Status.LOADING -> newsLoading.start()
                Status.ERROR -> {
                    serverDown()
                    binding.sfNews.stopShimmer()
                }
                Status.SUCCESS -> {

                    /**
                     * Setup RecyclerView
                     *
                     * @see RecyclerView.RecycledViewPool
                     * @see LinearLayoutManager
                     * @see NewsAdapter
                     */
                    binding.rvNews.apply {
                        setHasFixedSize(true)
                        setRecycledViewPool(RecyclerView.RecycledViewPool())
                        layoutManager = LinearLayoutManager(activity?.applicationContext)
                        adapter = NewsAdapter(it.data ?: listOf())
                    }
                    newsLoading.stop()
                }
            }
        }

        /**
         * Get news data
         * @see NewsViewModel.getNews
         */
        newsViewModel.getNews()
    }

    /**
     * Triggered function when activity is refreshed
     * @see ParentFragment.onRefresh
     * @see NewsViewModel.getNews
     */
    override fun onRefresh() {
        newsViewModel.getNews()
    }

    companion object {

        /**
         * Creating new instance of [NewsFragment]
         * @return
         */
        @JvmStatic
        fun newInstance() = NewsFragment()
    }
}