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
import android.util.AttributeSet
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient

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

        /**
         * Set Client
         */
        webViewClient = WebViewClient()
    }
}