/*
 * Copyright 2020 Nothing
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import com.github.nothing2512.anticorona.utils.toast
import com.github.nothing2512.anticorona.vo.Theme
import kotlinx.android.synthetic.*
import org.koin.android.ext.android.inject
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * [ParentActivity] class
 * @author Robet Atiq Maulana Rifqi
 *
 * @constructor [layout]
 *
 * @see AppCompatActivity
 */
abstract class ParentActivity<VDB : ViewDataBinding>(
    private val layout: Int
) : AppCompatActivity() {

    /**
     * variable device width
     * getting width of device
     *
     * @see DisplayMetrics
     */
    private var _deviceWidth = 0
    protected val deviceWidth: Int
        get() {
            if (_deviceWidth == 0) {
                val dm = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(dm)
                _deviceWidth = dm.widthPixels
            }
            return _deviceWidth
        }

    /**
     * variable preference
     * using for SharedPreference Helper
     *
     * @see Preference
     * @see inject
     */
    private val preference: Preference by inject()

    /**
     * variable binding
     * using for data binding in layout
     */
    protected lateinit var binding: VDB

    /**
     * function onCreate
     * triggering when activity created
     *
     * @param savedInstanceState
     *
     * @see AppCompatActivity.onCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Starting Kotlin Coroutine
         * @see launchMain
         */
        launchMain {

            /**
             * Getting app theme
             * @see Preference.theme
             */
            when (preference.theme) {
                Theme.DEFAULT.toString() -> {

                    /**
                     * Apply Saved Theme (DEFAULT)
                     *
                     * @see AppCompatActivity.setTheme
                     * @see getColorResource
                     * @see ColorDrawable
                     */
                    setTheme(R.style.AppTheme)
                    window?.statusBarColor = getColorResource(R.color.colorPrimaryDark)
                    supportActionBar?.setBackgroundDrawable(ColorDrawable(getColorResource(R.color.colorPrimary)))
                }
                Theme.OCEAN.toString() -> {

                    /**
                     * Apply Saved Theme (OCEAN)
                     *
                     * @see AppCompatActivity.setTheme
                     * @see getColorResource
                     * @see ColorDrawable
                     */
                    setTheme(R.style.Ocean)
                    window?.statusBarColor = getColorResource(R.color.colorPrimaryOceanDark)
                    supportActionBar?.setBackgroundDrawable(ColorDrawable(getColorResource(R.color.colorPrimaryOcean)))
                }
                else -> {

                    /**
                     * Apply Saved Theme (DEFAULT)
                     *
                     * @see AppCompatActivity.setTheme
                     * @see getColorResource
                     * @see ColorDrawable
                     */
                    setTheme(R.style.AppTheme)
                    window?.statusBarColor = getColorResource(R.color.colorPrimaryDark)
                    supportActionBar?.setBackgroundDrawable(ColorDrawable(getColorResource(R.color.colorPrimary)))
                }
            }

            /**
             * getting view data binding
             * @see getBinding
             */
            binding = getBinding(layout)

            /**
             * subscribing UI on child activity
             * @see subscribeUI
             */
            subscribeUI()
        }
    }

    /**
     * function setToolbarTitle
     *
     * @param resId
     *
     * @see AppCompatActivity.getString
     */
    protected fun setToolbarTitle(resId: Int) {
        supportActionBar?.title = getString(resId)
    }

    /**
     * function LiveData<T>.observe
     * observing LiveData value
     *
     * @param block
     *
     * @see LiveData.observe
     * @see Observer
     */
    protected fun <T> LiveData<T>.observe(block: (T) -> Unit) {
        observe(this@ParentActivity, Observer { block.invoke(it) })
    }

    /**
     * function getColorResource
     * getting color from resource id
     *
     * @param resId
     *
     * @see ContextCompat.getColor
     */
    private fun getColorResource(resId: Int) = ContextCompat.getColor(applicationContext, resId)

    /**
     * function onCreateOptionsMenu
     * triggered function when options menu are created
     *
     * @param menu
     *
     * @see AppCompatActivity.onCreateOptionsMenu
     * @see Menu
     *
     * @return Boolean
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    /**
     * function onOptionsItemSelected
     * triggered function when options menu are clicked
     *
     * @param item
     *
     * @see AppCompatActivity.onOptionsItemSelected
     * @see MenuItem
     *
     * @return Boolean
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {

            /**
             * Changing language
             * open language setting
             *
             * @see Intent
             * @see Settings.ACTION_LOCALE_SETTINGS
             * @see AppCompatActivity.startActivity
             */
            R.id.btChangeLanguage -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))

            /**
             * Changing Theme
             * open dialog to pick selected theme
             *
             * @see ThemeDialog
             */
            R.id.btChangeTheme ->
                ThemeDialog.newInstance(this).show(supportFragmentManager, ThemeDialog.TAG)

            /**
             * Triggering when home button clicked
             * finishing activity
             */
            android.R.id.home -> finish()
        }
        return true
    }

    /**
     * function serverDown
     * showing toast message when server is unreachable, down, or other errors.
     *
     * @see toast
     * @see getString
     */
    protected fun serverDown() = toast(getString(R.string.server_down))

    /**
     * function attachBaseContext
     * attaching new BaseContext
     *
     * @param newBase
     *
     * @see AppCompatActivity.attachBaseContext
     * @see CalligraphyContextWrapper.wrap
     */
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    /**
     * overridable function subscribeUI
     * must override this function into child for subscribing UI
     *
     * @see MainThread
     */
    @MainThread
    abstract fun subscribeUI()

    /**
     * function initializeBackButton
     * showing back button in toolbar
     */
    protected fun initializeBackButton() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    /**
     * function onDestroy
     * triggered function when activity is destroyed
     *
     * @see AppCompatActivity.onDestroy
     */
    override fun onDestroy() {
        super.onDestroy()
        clearFindViewByIdCache()
    }
}