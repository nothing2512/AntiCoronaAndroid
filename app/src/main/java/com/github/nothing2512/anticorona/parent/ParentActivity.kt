package com.github.nothing2512.anticorona.parent

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.MainThread
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
                    ContextCompat.getColor(applicationContext, R.color.colorPrimaryDark)
                }
                Theme.OCEAN.toString() -> {
                    setTheme(R.style.Ocean)
                    ContextCompat.getColor(applicationContext, R.color.colorPrimaryOceanDark)
                }
                else -> {
                    setTheme(R.style.AppTheme)
                    ContextCompat.getColor(applicationContext, R.color.colorPrimaryDark)
                }
            }

            binding = getBinding(layout)
            subscribeUI()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.btChangeLanguage -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            R.id.btChangeTheme -> {
                TODO("Show Dialog")
            }
            android.R.id.home -> finish()
        }
        return true
    }

    @MainThread
    abstract fun subscribeUI()

    protected fun initializeBackButton() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearFindViewByIdCache()
    }
}