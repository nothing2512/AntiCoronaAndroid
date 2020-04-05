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

package com.github.nothing2512.anticorona.utils

import android.annotation.SuppressLint
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import coil.transform.RoundedCornersTransformation
import com.github.nothing2512.anticorona.R
import coil.request.LoadRequestBuilder

/**
 * binding image to image view from any source
 * @param source
 * @see BindingAdapter
 */
@BindingAdapter("source")
fun ImageView.bind(source: Any) {

    /**
     * binding source to image view
     * @see bind
     */
    bind(source) {

        /**
         * adding placeholder to image view
         * @see LoadRequestBuilder.placeholder
         */
        placeholder(R.drawable.covid)
    }
}

/**
 * binding image to rounded image view from any source
 * @param roundSource
 * @see BindingAdapter
 */
@BindingAdapter("roundSource")
fun ImageView.bindRoundSource(roundSource: Any) {

    /**
     * binding source to image view
     * @see bind
     */
    bind(roundSource) {

        /**
         * transform image to rounded
         *
         * @see LoadRequestBuilder.transformations
         * @see RoundedCornersTransformation
         */
        transformations(RoundedCornersTransformation(300f))

        /**
         * adding placeholder to image view
         * @see LoadRequestBuilder.placeholder
         */
        placeholder(R.drawable.covid)
    }
}

/**
 * binding fragment into activity
 * @param activity
 * @param fragment
 * @see BindingAdapter
 */
@BindingAdapter("activity", "fragment")
fun FrameLayout.setFragment(activity: FragmentActivity?, fragment: Fragment?) {
    activity?.supportFragmentManager?.beginTransaction()
        ?.replace(this.id, fragment ?: Fragment())
        ?.commit()
}

/**
 * animating text view value
 * @param animateValue
 * @see BindingAdapter
 */
@BindingAdapter("animateValue")
fun TextView.animateValue(animateValue: String) {

    val oldValue = try {
        text.toString().toInt()
    } catch (e: NumberFormatException) {
        0
    }

    /**
     * animating value
     * @see animateValue
     */
    animateValue(oldValue, animateValue.toInt()) { text = it.toString() }
}

/**
 * binding text view
 * @param value
 * @see BindingAdapter
 */
@SuppressLint("SetTextI18n")
@BindingAdapter("bindText")
fun TextView.bindText(value: String) {
    text = "$text${if (value == "0") "" else value}"
}