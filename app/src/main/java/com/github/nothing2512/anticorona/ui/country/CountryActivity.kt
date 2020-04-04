package com.github.nothing2512.anticorona.ui.country

import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse
import com.github.nothing2512.anticorona.databinding.ActivityCountryBinding
import com.github.nothing2512.anticorona.parent.ParentActivity
import com.github.nothing2512.anticorona.ui.adapter.CountryAdapter
import com.github.nothing2512.anticorona.ui.dialog.CountryDialog
import com.github.nothing2512.anticorona.utils.Constants
import com.github.nothing2512.anticorona.vo.Status
import org.koin.android.ext.android.inject

class CountryActivity : ParentActivity<ActivityCountryBinding>(R.layout.activity_country) {

    private val countryViewModel: CountryViewModel by inject()
    private lateinit var countryLoading: CountryLoading

    override fun subscribeUI() {

        val data: List<CaseResponse> = intent.getParcelableArrayListExtra(Constants.EXTRA_COUNTRY)
        countryLoading = CountryLoading(binding)

        countryViewModel.repoCases.observe {
            when(it.status) {
                Status.LOADING -> countryLoading.start()
                Status.ERROR -> serverDown()
                Status.SUCCESS -> {
                    countryLoading.stop()
                    countryViewModel.setCases(it.data ?: listOf())
                }
            }
        }

        countryViewModel.cases.observe {
            val adapter = CountryAdapter(it) { item ->
                CountryDialog.newInstance(item).show(supportFragmentManager, CountryDialog.TAG)
            }
        }

        countryViewModel.setCases(data)
    }
}