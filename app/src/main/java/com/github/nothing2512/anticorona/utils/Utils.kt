@file:Suppress("unused")

package com.github.nothing2512.anticorona.utils

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun ShimmerFrameLayout.start() {
    show()
    startShimmer()
}

fun ShimmerFrameLayout.stop() {
    hide()
    stopShimmer()
}

fun <VDB : ViewDataBinding> Activity.getBinding(layout: Int): VDB =
    DataBindingUtil.setContentView(this, layout)

fun <VDB : ViewDataBinding> getBinding(
    inflater: LayoutInflater,
    layout: Int,
    parent: ViewGroup?
): VDB =
    DataBindingUtil.inflate(inflater, layout, parent, false)

fun <VDB : ViewDataBinding> getBinding(layout: Int, parent: ViewGroup?): VDB =
    DataBindingUtil.inflate(LayoutInflater.from(parent?.context), layout, parent, false)

fun <T> ViewModel.launchMain(block: suspend CoroutineScope.() -> T) {
    viewModelScope.launch {
        withContext(Dispatchers.Main) {
            block.invoke(this)
        }
    }
}

fun <T> launchMain(block: suspend CoroutineScope.() -> T) {
    CoroutineScope(Dispatchers.Main).launch {
        block.invoke(this)
    }
}

fun <T> idle(onHandle: () -> LiveData<T>) = EspressoIdlingResource.handle(onHandle)

fun <C : FragmentActivity> Activity.goto(clazz: Class<C>, data: (Intent.() -> Unit)? = null) {
    val intent = Intent(this.applicationContext, clazz)
    data?.let { intent.apply(data) }
    startActivity(intent)
}

fun <C : FragmentActivity> Fragment.goto(clazz: Class<C>, data: (Intent.() -> Unit)? = null) {
    val intent = Intent(this.activity?.applicationContext, clazz)
    data?.let { intent.apply(data) }
    startActivity(intent)
}

fun <C : FragmentActivity> goto(
    context: Context,
    clazz: Class<C>,
    data: (Intent.() -> Unit)? = null
) {
    val intent = Intent(context, clazz)
    data?.let { intent.apply(data) }
    context.startActivity(intent)
}

fun Activity.toast(msg: String?) =
    Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()

fun Fragment.toast(msg: String?) =
    Toast.makeText(activity?.applicationContext, msg, Toast.LENGTH_SHORT).show()

fun toast(context: Context, msg: String?) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

fun EditText.required(field: String) {
    error = "$field must not be empty"
}

fun postDelay(r: () -> Unit) = Handler().postDelayed(r, Constants.SERVICE_LATENCY_IN_MILLIS)

@Suppress("UNCHECKED_CAST")
fun animateValue(from: Int, to: Int, block: (Int) -> Unit) {
    ValueAnimator.ofInt(from, to).apply {
        duration = 1000
        addUpdateListener { block.invoke(it.animatedValue as Int) }
        start()
    }
}

fun SeekBar.animate(value: Int) {
    animateValue(progress, value) { progress = it }
}

fun <T> List<T>.toArrayList(): ArrayList<T> {
    val data = ArrayList<T>()
    forEach { data.add(it) }
    return data
}