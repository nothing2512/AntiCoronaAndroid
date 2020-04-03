package com.github.nothing2512.anticorona.parent

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.ui.dialog.ThemeDialog
import com.github.nothing2512.anticorona.utils.Preference
import com.github.nothing2512.anticorona.utils.getBinding
import com.github.nothing2512.anticorona.utils.launchMain
import com.github.nothing2512.anticorona.vo.Theme
import kotlinx.android.synthetic.*
import org.koin.android.ext.android.inject
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

abstract class ParentActivity<VDB : ViewDataBinding>(private val layout: Int) :
    AppCompatActivity() {

    private var _deviceWidth = 0
    private val preference: Preference by inject()

    protected lateinit var binding: VDB
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

            when (preference.theme) {
                Theme.DEFAULT.toString() -> {
                    setTheme(R.style.AppTheme)
                    window?.statusBarColor = getColorResource(R.color.colorPrimaryDark)
                    supportActionBar?.setBackgroundDrawable(ColorDrawable(getColorResource(R.color.colorPrimary)))
                }
                Theme.OCEAN.toString() -> {
                    setTheme(R.style.Ocean)
                    window?.statusBarColor = getColorResource(R.color.colorPrimaryOceanDark)
                    supportActionBar?.setBackgroundDrawable(ColorDrawable(getColorResource(R.color.colorPrimaryOcean)))
                }
                else -> {
                    setTheme(R.style.AppTheme)
                    window?.statusBarColor = getColorResource(R.color.colorPrimaryDark)
                    supportActionBar?.setBackgroundDrawable(ColorDrawable(getColorResource(R.color.colorPrimary)))
                }
            }

            binding = getBinding(layout)
            subscribeUI()
        }
    }

    protected fun setToolbarTitle(resId: Int) {
        supportActionBar?.title = getString(resId)
    }

    protected fun <T> LiveData<T>.observe(block: (T) -> Unit) {
        observe(this@ParentActivity, Observer { block.invoke(it) })
    }

    private fun getColorResource(resId: Int) = ContextCompat.getColor(applicationContext, resId)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.btChangeLanguage -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            R.id.btChangeTheme ->
                ThemeDialog.newInstance(this).show(supportFragmentManager, ThemeDialog.TAG)
            android.R.id.home -> finish()
        }
        return true
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
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