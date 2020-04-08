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

import android.widget.FrameLayout
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import coil.request.LoadRequestBuilder
import coil.transform.RoundedCornersTransformation
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.component.AnimatedTextView

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
 * @see AnimatedTextView
 */
@Suppress("LocalVariableName")
@BindingAdapter("animateValue")
fun AnimatedTextView.animateValue(animateValue: String) {
    try {
        if (value != animateValue.toInt()) {
            animateValue(value, animateValue.toInt()) {
                text = originalText.replace(Constants.TV_REG, it.toString())
            }
            this.value = animateValue.toInt()
        }

        if (animateValue.toInt() == 0) text = originalText.replace(Constants.TV_REG, "0")
    } catch (e: Exception) {
        text = originalText.replace(Constants.TV_REG, "0")
    }

}