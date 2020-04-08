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

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.View
import android.webkit.*
import com.github.nothing2512.anticorona.utils.hide
import com.github.nothing2512.anticorona.utils.show

/**
 * [ConfiguratedWebView] class
 * @author Robet Atiq Maulana Rifqi
 * @see WebView
 */
class ConfiguratedWebView : WebView {

    private var onError: ((url: String) -> Unit)? = null

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
            builtInZoomControls = true
            displayZoomControls = false
            setSupportZoom(true)
        }

        /**
         * Set ScrollBar style
         */
        scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
    }

    /**
     * Set on error listener
     * @param block
     */
    fun onError(block: (url: String) -> Unit) {
        onError = block
    }

    /**
     * load url function
     */
    fun load(url: String, onFinish: () -> Unit) {

        /**
         * Set Web View Client
         * @see WebClient
         */
        webViewClient = WebClient(onFinish, onError)

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
        private val onFinish: () -> Unit,
        private val onError: ((url: String) -> Unit)?
    ) : WebViewClient() {

        /**
         * triggered function when starting load page
         *
         * @param view
         * @param url
         * @param favicon
         */
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {

            /**
             * Hiding View
             * @see View.hide
             */
            view?.hide()
        }

        /**
         * triggered function when failure to load url
         *
         * @param view
         * @param request
         * @param error
         */
        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            onError?.invoke(view?.url ?: "https://google.com")
        }

        /**
         * triggered function when finishing load page
         *
         * @param view
         * @param url
         */
        override fun onPageFinished(view: WebView?, url: String?) {

            /**
             * Showing web page
             */
            view?.show()
            onFinish.invoke()
            super.onPageFinished(view, url)
        }
    }
}