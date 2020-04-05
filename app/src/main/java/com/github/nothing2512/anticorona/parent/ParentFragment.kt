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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.appcompat.view.ContextThemeWrapper
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.utils.Preference
import com.github.nothing2512.anticorona.utils.getBinding
import com.github.nothing2512.anticorona.utils.launchMain
import com.github.nothing2512.anticorona.utils.toast
import com.github.nothing2512.anticorona.vo.Theme
import kotlinx.android.synthetic.*
import org.koin.android.ext.android.inject

/**
 * [ParentFragment] class
 * @author Robet Atiq Maulana Rifqi
 *
 * @constructor [layout]
 *
 * @see Fragment
 */
abstract class ParentFragment<VDB : ViewDataBinding>(private val layout: Int) : Fragment() {

    /**
     * view data binding variable
     * @see ViewDataBinding
     */
    protected lateinit var binding: VDB

    /**
     * shared preference variable
     *
     * @see Preference
     * @see inject
     */
    private val preference: Preference by inject()

    /**
     * function onCreateView
     * triggered function before view created
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     *
     * @see Fragment.onCreateView
     *
     * @return View
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * getting theme wrapper
         *
         * @see Theme
         * @see ContextThemeWrapper
         */
        val wrapper = when (preference.theme) {
            Theme.DEFAULT.toString() -> ContextThemeWrapper(activity, R.style.AppTheme)
            Theme.OCEAN.toString() -> ContextThemeWrapper(activity, R.style.Ocean)
            else -> ContextThemeWrapper(activity, R.style.AppTheme)
        }

        /**
         * Getting view data binding
         *
         * @see getBinding
         * @see LayoutInflater.cloneInContext
         */
        binding = getBinding(inflater.cloneInContext(wrapper), layout, container)

        return binding.root
    }

    /**
     * function onViewCreated
     * triggered function after view is created
     *
     * @param view
     * @param savedInstanceState
     *
     * @see Fragment.onViewCreated
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Starting kotlin coroutines
         * @see launchMain
         */
        launchMain {

            /**
             * subscribing UI on child dialog
             * @see subscribeUI
             */
            subscribeUI(savedInstanceState)
        }
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
        observe(this@ParentFragment, Observer { block.invoke(it) })
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
     * overridable function onRefresh
     * function to execute code when refresh is triggered
     */
    protected open fun onRefresh() {}

    /**
     * function refresh
     * trigger refreshing fragment
     * @see onRefresh
     */
    fun refresh() {
        onRefresh()
    }

    /**
     * overridable function subscribeUI
     * must override this function into child for subscribing UI
     *
     * @see MainThread
     */
    @MainThread
    abstract fun subscribeUI(bundle: Bundle?)

    /**
     * function onDestroy
     * triggered function when activity is destroyed
     *
     * @see Fragment.onDestroy
     */
    override fun onDestroy() {
        super.onDestroy()
        clearFindViewByIdCache()
    }
}