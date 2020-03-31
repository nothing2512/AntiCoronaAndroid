package com.github.nothing2512.anticorona.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import coil.api.load
import coil.request.LoadRequestBuilder
import com.github.nothing2512.anticorona.R

@BindingAdapter("source")
fun ImageView.bind(source: Any) {

    val builder: LoadRequestBuilder.() -> Unit = { placeholder(R.mipmap.ic_launcher) }

    when (source) {
        is String -> {
            if (source != "") load(source, builder = builder)
            else load(R.mipmap.ic_launcher, builder = builder)
        }
        is Int -> load(source, builder = builder)
        is Uri -> load(source, builder = builder)
        is Drawable -> load(source, builder = builder)
        is Bitmap -> load(source, builder = builder)
        else -> load(R.mipmap.ic_launcher, builder = builder)
    }
}

@BindingAdapter("activity", "fragment")
fun FrameLayout.setFragment(activity: FragmentActivity?, fragment: Fragment?) {
    activity?.supportFragmentManager?.beginTransaction()
        ?.replace(this.id, fragment ?: Fragment())
        ?.commit()
}