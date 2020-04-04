package com.github.nothing2512.anticorona.ui.news

import android.os.Bundle
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.databinding.FragmentNewsBinding
import com.github.nothing2512.anticorona.parent.ParentFragment
import com.github.nothing2512.anticorona.ui.adapter.NewsAdapter
import com.github.nothing2512.anticorona.vo.Status
import org.koin.android.ext.android.inject

class NewsFragment : ParentFragment<FragmentNewsBinding>(R.layout.fragment_news) {

    private val newsViewModel: NewsViewModel by inject()
    private lateinit var newsLoading: NewsLoading

    override fun subscribeUI(bundle: Bundle?) {

        newsLoading = NewsLoading(binding)

        newsViewModel.news.observe {
            when(it.status) {
                Status.LOADING -> newsLoading.start()
                Status.ERROR -> serverDown()
                Status.SUCCESS -> {
                    val adapter = NewsAdapter(it.data ?: listOf())
                    newsLoading.stop()
                }
            }
        }

        newsViewModel.getNews()
    }

    override fun onRefresh() {
        newsViewModel.getNews()
    }

    companion object {

        @JvmStatic
        fun newInstance() = NewsFragment()
    }
}