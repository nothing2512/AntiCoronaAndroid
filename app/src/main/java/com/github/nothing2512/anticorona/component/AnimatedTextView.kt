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

package com.github.nothing2512.anticorona.component

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * [AnimatedTextView] class
 * @author Robet Atiq Maulana Rifqi
 * @see AppCompatTextView
 */
class AnimatedTextView : AppCompatTextView {

    /**
     * Declaring read - write variable
     */
    var value: Int = 0
    var originalText: String = text.toString()

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
}