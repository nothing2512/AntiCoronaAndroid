package com.github.nothing2512.anticorona.parent

import android.view.View
import com.facebook.shimmer.ShimmerFrameLayout
import com.github.nothing2512.anticorona.utils.hide
import com.github.nothing2512.anticorona.utils.show
import com.github.nothing2512.anticorona.utils.start
import com.github.nothing2512.anticorona.utils.stop

abstract class ParentLoading {

    protected fun <V : View> start(shimmer: ShimmerFrameLayout, vararg view: V) {
        shimmer.start()
        view.forEach { it.hide() }
    }

    protected fun <V : View> stop(shimmer: ShimmerFrameLayout, vararg view: V) {
        shimmer.stop()
        view.forEach { it.show() }
    }
}