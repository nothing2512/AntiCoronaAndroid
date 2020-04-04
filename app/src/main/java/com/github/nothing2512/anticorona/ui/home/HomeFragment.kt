package com.github.nothing2512.anticorona.ui.home

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.databinding.FragmentHomeBinding
import com.github.nothing2512.anticorona.parent.ParentFragment
import com.github.nothing2512.anticorona.ui.adapter.CountryAdapter
import com.github.nothing2512.anticorona.ui.adapter.ProvinceAdapter
import com.github.nothing2512.anticorona.ui.country.CountryActivity
import com.github.nothing2512.anticorona.ui.dialog.CountryDialog
import com.github.nothing2512.anticorona.ui.dialog.ProvinceDialog
import com.github.nothing2512.anticorona.ui.province.ProvinceActivity
import com.github.nothing2512.anticorona.utils.Constants
import com.github.nothing2512.anticorona.utils.animate
import com.github.nothing2512.anticorona.utils.goto
import com.github.nothing2512.anticorona.utils.toArrayList
import com.github.nothing2512.anticorona.vo.Status
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : ParentFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by sharedViewModel()
    private lateinit var loading: HomeLoading

    override fun subscribeUI(bundle: Bundle?) {

        loading = HomeLoading(binding)

        observeHome()
        observeProvince()
        observeGlobal()
        observeCountry()

        binding.sbHomeDeath.isEnabled = false
        binding.sbHomeRecovered.isEnabled = false

        homeViewModel.getIndonesianCase()
    }

    private fun observeHome() {
        homeViewModel.indonesianCase.observe {
            /**
             * Used: cases, covered, death
             */
            when (it.status) {
                Status.LOADING -> loading.startAll()
                Status.ERROR -> serverDown()
                Status.SUCCESS -> {
                    loading.indonesianStop()
                    binding.indonesian = it.data
                    homeViewModel.getProvinceCase()
                }
            }
        }
    }

    private fun observeProvince() {
        homeViewModel.provinceCase.observe {
            when (it.status) {
                Status.LOADING -> loading.provinceStart()
                Status.ERROR -> serverDown()
                Status.SUCCESS -> {

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

    private fun observeGlobal() {
        homeViewModel.globalCase.observe {
            when (it.status) {
                Status.LOADING -> loading.globalStart()
                Status.ERROR -> serverDown()
                Status.SUCCESS -> {

                    val recovered = it.data?.cases?.div(it.data.recovered)
                    val death = it.data?.cases?.div(it.data.death)

                    binding.global = it.data
                    binding.sbHomeRecovered.animate(recovered ?: 0)
                    binding.sbHomeDeath.animate(death ?: 0)

                    loading.globalStop()
                    homeViewModel.getCountryCase()
                }
            }
        }
    }

    private fun observeCountry() {
        homeViewModel.countryCase.observe {
            when (it.status) {
                Status.LOADING -> loading.countryStart()
                Status.ERROR -> serverDown()
                Status.SUCCESS -> {
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

    override fun onRefresh() {
        homeViewModel.getIndonesianCase()
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}