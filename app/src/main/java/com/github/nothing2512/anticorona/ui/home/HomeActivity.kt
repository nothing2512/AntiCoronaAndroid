package com.github.nothing2512.anticorona.ui.home

import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.databinding.ActivityHomeBinding
import com.github.nothing2512.anticorona.parent.ParentActivity
import com.github.nothing2512.anticorona.parent.ParentFragment
import com.github.nothing2512.anticorona.utils.Constants
import com.github.nothing2512.anticorona.utils.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : ParentActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val homeViewModel: HomeViewModel by viewModel()
    private var isHome = true

    override fun subscribeUI() {

        binding.homeSwipe.setOnRefreshListener {
            (homeViewModel.fragment.value as ParentFragment<*>).refresh()
            binding.homeSwipe.isRefreshing = false
        }

        binding.lifecycleOwner = this
        binding.activity = this
        binding.viewModel = homeViewModel

        binding.homeBottomNav.setup(deviceWidth, binding.lin)

        binding.homeBottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.btNavFaq -> {
                    setToolbarTitle(R.string.toolbar_faq)
                    homeViewModel.setFragment(Constants.FRAGMENT_FAQ)
                    binding.homeBottomNav.update(binding.homeButtonNav, Constants.FAQS)
                    binding.homeButtonNav.bind(R.drawable.faq_white)
                    isHome = false
                }
                R.id.btNavHome -> {
                    setToolbarTitle(R.string.toolbar_cases)
                    homeViewModel.setFragment(Constants.FRAGMENT_HOME)
                    binding.homeBottomNav.update(binding.homeButtonNav, Constants.HOME)
                    binding.homeButtonNav.bind(R.drawable.home_white)
                    isHome = true
                }
                R.id.btNavNews -> {
                    setToolbarTitle(R.string.toolbar_news)
                    homeViewModel.setFragment(Constants.FRAGMENT_NEWS)
                    binding.homeBottomNav.update(binding.homeButtonNav, Constants.NEWS)
                    binding.homeButtonNav.bind(R.drawable.news_white)
                    isHome = false
                }
            }
            true
        }

        binding.homeBottomNav.selectedItemId = R.id.btNavHome
    }

    override fun onBackPressed() {
        if (isHome) super.onBackPressed()
        else binding.homeBottomNav.selectedItemId = R.id.btNavHome
    }

}