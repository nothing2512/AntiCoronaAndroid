package com.github.nothing2512.anticorona.ui.home

import android.os.Bundle
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.databinding.FragmentHomeBinding
import com.github.nothing2512.anticorona.parent.ParentFragment

class HomeFragment : ParentFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun subscribeUI(bundle: Bundle?) {

    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}