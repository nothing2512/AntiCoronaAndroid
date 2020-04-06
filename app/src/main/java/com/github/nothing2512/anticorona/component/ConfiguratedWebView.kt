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

@file:SuppressLint("SetJavaScriptEnabled")

package com.github.nothing2512.anticorona.component

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import com.github.nothing2512.anticorona.utils.hide
import com.github.nothing2512.anticorona.utils.show

/**
 * [ConfiguratedWebView] class
 * @author Robet Atiq Maulana Rifqi
 * @see WebView
 */
class ConfiguratedWebView : WebView {

    /**
     * @constructor
     * @param context
     */
    constructor(context: Context) : super(context)

    /**
     * @constructor
     * @param context
     * @param attrs
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    /**
     * @constructor
     * @param context
     * @param attrs
     * @param defStyleArr
     */
    constructor(context: Context, attrs: AttributeSet, defStyleArr: Int) : super(
        context,
        attrs,
        defStyleArr
    )

    init {

        /**
         * Configure WebView
         */
        settings.apply {
            loadsImagesAutomatically = true
            javaScriptEnabled = true
            domStorageEnabled = true
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
        }

        /**
         * Set ScrollBar style
         */
        scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
    }

    /**
     * load url function
     */
    fun load(url: String, loadingIcon: ImageView) {

        /**
         * Set Web View Client
         * @see WebClient
         */
        webViewClient = WebClient(loadingIcon)

        /**
         * Load Web Url
         * @see WebView.loadUrl
         */
        loadUrl(url)
    }

    /**
     * [WebClient] class
     * @see WebViewClient
     */
    private class WebClient(
        private val imageView: ImageView
    ) : WebViewClient() {

        /**
         * Declare boolean variable
         */
        private var isLoading = true

        /**
         * triggered function when starting load page
         *
         * @param view
         * @param url
         * @param favicon
         */
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

            /**
             * Start animating image
             */
            oneToZero()

            /**
             *
             */
            view?.hide()
        }

        /**
         * triggered function when finishing load page
         *
         * @param view
         * @param url
         */
        override fun onPageFinished(view: WebView?, url: String?) {

            /**
             * Set isLoading to false
             */
            isLoading = false

            /**
             * Showing web page
             */
            view?.show()
            imageView.hide()
            super.onPageFinished(view, url)
        }

        private fun oneToZero() {
            imageView.animate().apply {
                alpha(0f)
                duration = 1000L
                setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        if (isLoading) zeroToOne()
                    }
                })
            }.start()
        }

        private fun zeroToOne() {
            imageView.animate().apply {
                alpha(1f)
                duration = 1000L
                setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        if (isLoading) oneToZero()
                    }
                })
            }.start()
        }
    }
}