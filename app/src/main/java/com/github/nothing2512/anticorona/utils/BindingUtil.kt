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

@BindingAdapter("source")
fun ImageView.bind(source: Any) {

    bind(source) { placeholder(R.mipmap.ic_launcher) }
}

@BindingAdapter("roundSource")
fun ImageView.bindRoundSource(roundSource: Any) {
    bind(roundSource) {
        transformations(RoundedCornersTransformation(300f))
        placeholder(R.mipmap.ic_launcher)
    }
}

@BindingAdapter("activity", "fragment")
fun FrameLayout.setFragment(activity: FragmentActivity?, fragment: Fragment?) {
    activity?.supportFragmentManager?.beginTransaction()
        ?.replace(this.id, fragment ?: Fragment())
        ?.commit()
}

@BindingAdapter("animateValue")
fun TextView.animateValue(animateValue: String) {
    val oldValue = try {
        text.toString().toInt()
    } catch (e: NumberFormatException) {
        0
    }
    animateValue(oldValue, animateValue.toInt()) { text = it.toString() }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("bindText")
fun TextView.bindText(value: String) {
    text = "$text${if (value == "0") "" else value}"
}