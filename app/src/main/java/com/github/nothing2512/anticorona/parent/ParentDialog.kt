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

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.appcompat.view.ContextThemeWrapper
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.ViewDataBinding
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.utils.Preference
import com.github.nothing2512.anticorona.utils.getBinding
import com.github.nothing2512.anticorona.utils.launchMain
import com.github.nothing2512.anticorona.vo.Theme
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.*
import org.koin.android.ext.android.inject

/**
 * [ParentDialog] class
 * @author Robet Atiq Maulana Rifqi
 *
 * @constructor [layout]
 *
 * @see BottomSheetDialogFragment
 */
abstract class ParentDialog<VDB : ViewDataBinding>(private val layout: Int) :
    BottomSheetDialogFragment() {

    /**
     * variable view data binding
     * @see ViewDataBinding
     */
    protected lateinit var binding: VDB

    /**
     * shared preference variable
     *
     * @see Preference
     * @see inject
     */
    protected val preference: Preference by inject()

    /**
     * function onCreateView
     * triggered function before view created
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     *
     * @see BottomSheetDialogFragment.onCreateView
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
     * @see BottomSheetDialogFragment.onViewCreated
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
            subscribeUI()
        }
    }

    /**
     * triggered function when activity is created
     *
     * @param savedInstanceState
     *
     * @see BottomSheetDialogFragment.onActivityCreated
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /**
         * Set horizontal margin to dialog
         *
         * @see CoordinatorLayout.LayoutParams
         * @see Resources.getDimensionPixelSize
         */
        val parent = view?.parent as View
        val lp = parent.layoutParams as CoordinatorLayout.LayoutParams
        lp.setMargins(
            resources.getDimensionPixelSize(R.dimen.spacing),
            0,
            resources.getDimensionPixelSize(R.dimen.spacing),
            0
        )
        parent.layoutParams = lp
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
     * function onDestroy
     * triggered function when dialog is destroyed
     *
     * @see BottomSheetDialogFragment.onDestroy
     */
    override fun onDestroy() {
        super.onDestroy()
        clearFindViewByIdCache()
    }
}