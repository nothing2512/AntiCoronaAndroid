package com.github.nothing2512.anticorona.ui.home

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.databinding.FragmentHomeBinding
import com.github.nothing2512.anticorona.parent.ParentFragment
import com.github.nothing2512.anticorona.ui.adapter.ProvinceAdapter
import com.github.nothing2512.anticorona.ui.province.ProvinceActivity
import com.github.nothing2512.anticorona.utils.Constants
import com.github.nothing2512.anticorona.utils.goto
import com.github.nothing2512.anticorona.utils.toArrayList
import com.github.nothing2512.anticorona.utils.toast
import com.github.nothing2512.anticorona.vo.Status
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : ParentFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by sharedViewModel()

    override fun subscribeUI(bundle: Bundle?) {

        homeViewModel.indonesianCase.observe {
            /**
             * Used: cases, covered, death
             */
            when (it.status) {
                Status.LOADING -> {
                }
                Status.ERROR -> toast(it.message)
                Status.SUCCESS -> {
                    binding.indonesian = it.data
                    homeViewModel.getGlobalCase()
                }
            }
        }

        homeViewModel.globalCase.observe {
            /**
             * Used: cases, covered, death
             */
            when (it.status) {
                Status.LOADING -> {
                }
                Status.ERROR -> toast(it.message)
                Status.SUCCESS -> {
                    binding.global = it.data
                    homeViewModel.getProvinceCase()
                }
            }
        }

        homeViewModel.provinceCase.observe {
            /**
             * Used: cases, covered, death, location
             */
            when (it.status) {
                Status.LOADING -> {
                }
                Status.ERROR -> toast(it.message)
                Status.SUCCESS -> {

                    binding.rvHomeProvince.apply {
                        isNestedScrollingEnabled = true
                        layoutManager = LinearLayoutManager(activity?.applicationContext)
                        adapter = ProvinceAdapter(it.data?.slice(0..5) ?: listOf())
                        clearFocus()
                    }

                    binding.tvHomeProvinceHighlight.setOnClickListener { _ ->
                        goto(ProvinceActivity::class.java) {
                            putParcelableArrayListExtra(
                                Constants.EXTRA_PROVINCE,
                                it.data?.toArrayList()
                            )
                        }
                    }

                    homeViewModel.getCountryCase()
                }
            }
        }

        homeViewModel.countryCase.observe {
            /**
             * Used: cases, covered, death, location, flag
             */
            when (it.status) {
                Status.LOADING -> {
                }
                Status.ERROR -> toast(it.message)
                Status.SUCCESS -> {
                }
            }
        }

        binding.sbHomeDeath.isEnabled = false
        binding.sbHomeRecovered.isEnabled = false

        homeViewModel.getIndonesianCase()
    }

    override fun onRefresh() {
        toast("Refreshed")
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}