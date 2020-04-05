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

import android.view.View
import com.facebook.shimmer.ShimmerFrameLayout
import com.github.nothing2512.anticorona.utils.hide
import com.github.nothing2512.anticorona.utils.show
import com.github.nothing2512.anticorona.utils.start
import com.github.nothing2512.anticorona.utils.stop

/**
 * [ParentLoading] class
 * @author Robet Atiq Maulana Rifqi
 */
abstract class ParentLoading {

    /**
     * function start
     * start loading
     *
     * @param shimmer
     * @param view
     */
    protected fun <V : View> start(shimmer: ShimmerFrameLayout, vararg view: V) {

        /**
         * starting shimmer effect
         * @see ShimmerFrameLayout.start
         */
        shimmer.start()

        /**
         * hiding related view
         * @see View.hide
         */
        view.forEach { it.hide() }
    }

    /**
     * function stop
     * stop loading
     *
     * @param shimmer
     * @param view
     */
    protected fun <V : View> stop(shimmer: ShimmerFrameLayout, vararg view: V) {

        /**
         * stopping shimmer effect
         * @see ShimmerFrameLayout.stop
         */
        shimmer.stop()

        /**
         * showing related view
         * @see View.show
         */
        view.forEach { it.show() }
    }
}