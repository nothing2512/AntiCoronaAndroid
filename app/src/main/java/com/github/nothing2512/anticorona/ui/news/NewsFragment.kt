package com.github.nothing2512.anticorona.ui.news

import android.os.Bundle
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.databinding.FragmentNewsBinding
import com.github.nothing2512.anticorona.parent.ParentFragment

class NewsFragment : ParentFragment<FragmentNewsBinding>(R.layout.fragment_news) {
    override fun subscribeUI(bundle: Bundle?) {

    }

    companion object {

        @JvmStatic
        fun newInstance() = NewsFragment()
    }
}