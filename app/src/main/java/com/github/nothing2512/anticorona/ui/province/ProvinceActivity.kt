package com.github.nothing2512.anticorona.ui.province

import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse
import com.github.nothing2512.anticorona.databinding.ActivityProvinceBinding
import com.github.nothing2512.anticorona.parent.ParentActivity
import com.github.nothing2512.anticorona.ui.adapter.ProvinceAdapter
import com.github.nothing2512.anticorona.ui.dialog.ProvinceDialog
import com.github.nothing2512.anticorona.utils.Constants
import com.github.nothing2512.anticorona.vo.Status
import org.koin.android.ext.android.inject

class ProvinceActivity : ParentActivity<ActivityProvinceBinding>(R.layout.activity_province) {

    private val provinceViewModel: ProvinceViewModel by inject()
    private lateinit var provinceLoading: ProvinceLoading

    override fun subscribeUI() {

        val data: List<CaseResponse> = intent.getParcelableArrayListExtra(Constants.EXTRA_PROVINCE)
        provinceLoading = ProvinceLoading(binding)

        provinceViewModel.repoCase.observe {
            when(it.status) {
                Status.LOADING -> provinceLoading.start()
                Status.ERROR -> serverDown()
                Status.SUCCESS -> {
                    provinceViewModel.setCases(it.data ?: listOf())
                    provinceLoading.stop()
                }
            }
        }

        provinceViewModel.cases.observe {
            val adapter = ProvinceAdapter(it) { item ->
                ProvinceDialog.newInstance(item).show(supportFragmentManager, ProvinceDialog.TAG)
            }
        }

        provinceViewModel.setCases(data)
    }
}