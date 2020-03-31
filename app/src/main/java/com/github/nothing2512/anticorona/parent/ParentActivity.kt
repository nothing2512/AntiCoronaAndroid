package com.github.nothing2512.anticorona.parent

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.utils.Preference
import com.github.nothing2512.anticorona.utils.getBinding
import com.github.nothing2512.anticorona.utils.launchMain
import com.github.nothing2512.anticorona.vo.Theme
import kotlinx.android.synthetic.*
import org.koin.android.ext.android.inject

abstract class ParentActivity<VDB : ViewDataBinding>(private val layout: Int) :
    AppCompatActivity() {

    private var _deviceWidth = 0

    protected lateinit var binding: VDB
    protected val preference: Preference by inject()
    protected val deviceWidth: Int
        get() {
            if (_deviceWidth == 0) {
                val dm = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(dm)
                _deviceWidth = dm.widthPixels
            }
            return _deviceWidth
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchMain {

            window?.statusBarColor = when (preference.theme) {
                Theme.DEFAULT.toString() -> {
                    setTheme(R.style.AppTheme)
                    ContextCompat.getColor(applicationContext, R.color.colorPrimary)
                }
                Theme.OCEAN.toString() -> {
                    setTheme(R.style.Ocean)
                    ContextCompat.getColor(applicationContext, R.color.colorPrimaryOcean)
                }
                else -> {
                    setTheme(R.style.AppTheme)
                    ContextCompat.getColor(applicationContext, R.color.colorPrimary)
                }
            }

            binding = getBinding(layout)
            subscribeUI()
        }
    }

    abstract fun subscribeUI()

    override fun onDestroy() {
        super.onDestroy()
        clearFindViewByIdCache()
    }
}