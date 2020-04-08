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

@file:Suppress("unused")

package com.github.nothing2512.anticorona.utils

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import coil.api.load
import coil.request.LoadRequestBuilder
import com.facebook.shimmer.ShimmerFrameLayout
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.data.local.CoronaDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * showing view
 * @see View.setVisibility
 */
fun View.show() {
    this.visibility = View.VISIBLE
}

/**
 * hiding view
 * @see View.setVisibility
 */
fun View.hide() {
    this.visibility = View.GONE
}

/**
 * starting shimmer effect
 * @see ShimmerFrameLayout.startShimmer
 * @see show
 */
fun ShimmerFrameLayout.start() {
    show()
    startShimmer()
}

/**
 * stopping shimmer effect
 * @see ShimmerFrameLayout.stopShimmer
 * @see hide
 */
fun ShimmerFrameLayout.stop() {
    hide()
    stopShimmer()
}

/**
 * getting view data binding
 *
 * @param layout
 *
 * @see DataBindingUtil.setContentView
 *
 * @return
 */
fun <VDB : ViewDataBinding> Activity.getBinding(layout: Int): VDB =
    DataBindingUtil.setContentView(this, layout)

/**
 * getting view data binding
 *
 * @param inflater
 * @param layout
 * @param parent
 *
 * @see DataBindingUtil.inflate
 *
 * @return
 */
fun <VDB : ViewDataBinding> getBinding(
    inflater: LayoutInflater,
    layout: Int,
    parent: ViewGroup?
): VDB =
    DataBindingUtil.inflate(inflater, layout, parent, false)

/**
 * get view data binding
 *
 * @param layout
 * @param parent
 *
 * @see DataBindingUtil.inflate
 *
 * @return
 */
fun <VDB : ViewDataBinding> getBinding(layout: Int, parent: ViewGroup?): VDB =
    DataBindingUtil.inflate(LayoutInflater.from(parent?.context), layout, parent, false)

/**
 * starting viewModel CoroutineScope
 *
 * @param block
 *
 * @see ViewModel.viewModelScope
 * @see Dispatchers.Main
 */
fun <T> ViewModel.launchMain(block: suspend CoroutineScope.() -> T) {
    viewModelScope.launch {
        withContext(Dispatchers.Main) {
            block.invoke(this)
        }
    }
}

/**
 * starting coroutineScope
 *
 * @param block
 *
 * @see CoroutineScope.launch
 * @see Dispatchers.Main
 */
fun <T> launchMain(block: suspend CoroutineScope.() -> T) {
    CoroutineScope(Dispatchers.Main).launch {
        block.invoke(this)
    }
}

/**
 * Binding image view
 *
 * @param source
 * @param builder
 */
fun ImageView.bind(source: Any, builder: LoadRequestBuilder.() -> Unit) {

    /**
     * Loading image view
     *
     * @see ImageView.load
     */
    when (source) {
        is String -> {
            if (source != "") load(source, builder = builder)
            else load(R.drawable.covid, builder = builder)
        }
        is Int -> load(source, builder = builder)
        is Uri -> load(source, builder = builder)
        is Drawable -> load(source, builder = builder)
        is Bitmap -> load(source, builder = builder)
        else -> load(R.drawable.covid, builder = builder)
    }
}

/**
 * Idling espressoIdlingResource
 *
 * @param onHandle
 *
 * @see EspressoIdlingResource.handle
 */
fun <T> idle(onHandle: () -> LiveData<T>) = EspressoIdlingResource.handle(onHandle)

/**
 * starting new activity from another activity
 *
 * @param clazz
 * @param data
 *
 * @see Intent
 */
fun <C : FragmentActivity> Activity.goto(clazz: Class<C>, data: (Intent.() -> Unit)? = null) {
    val intent = Intent(this.applicationContext, clazz)
    data?.let { intent.apply(data) }
    startActivity(intent)
}

/**
 * starting new activity from fragment
 *
 * @param clazz
 * @param data
 *
 * @see Intent
 */
fun <C : FragmentActivity> Fragment.goto(clazz: Class<C>, data: (Intent.() -> Unit)? = null) {
    val intent = Intent(this.activity?.applicationContext, clazz)
    data?.let { intent.apply(data) }
    startActivity(intent)
}

/**
 * starting new activity from another class
 *
 * @param context
 * @param clazz
 * @param data
 *
 * @see Intent
 */
fun <C : FragmentActivity> goto(
    context: Context,
    clazz: Class<C>,
    data: (Intent.() -> Unit)? = null
) {
    val intent = Intent(context, clazz)
    data?.let { intent.apply(data) }
    context.startActivity(intent)
}

/**
 * showing toast from activity
 *
 * @param msg
 *
 * @see Toast.makeText
 */
fun Activity.toast(msg: String?) =
    Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()

/**
 * showing toast from fragment
 *
 * @param msg
 *
 * @see Toast.makeText
 */
fun Fragment.toast(msg: String?) =
    Toast.makeText(activity?.applicationContext, msg, Toast.LENGTH_SHORT).show()

/**
 * showing toast from another class
 *
 * @param context
 * @param msg
 *
 * @see Toast.makeText
 */
fun toast(context: Context, msg: String?) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

/**
 * set error message from edit text
 *
 * @param field
 *
 * @see EditText.setError
 */
fun EditText.required(field: String) {
    error = "$field must not be empty"
}

/**
 * handling post Delayed
 *
 * @param r
 *
 * @see Handler.postDelayed
 */
fun postDelay(r: () -> Unit) = Handler().postDelayed(r, Constants.SERVICE_LATENCY_IN_MILLIS)

/**
 * handling post Delayed
 *
 * @param delay
 * @param r
 *
 * @see Handler.postDelayed
 */
fun postDelay(delay: Long, r: () -> Unit) = Handler().postDelayed(r, delay)

/**
 * Animating value
 *
 * @param from
 * @param to
 * @param block
 *
 * @see ValueAnimator.ofInt
 */
@Suppress("UNCHECKED_CAST")
fun animateValue(from: Int, to: Int, block: (Int) -> Unit) {
    ValueAnimator.ofInt(from, to).apply {
        duration = 1000
        addUpdateListener { block.invoke(it.animatedValue as Int) }
        start()
    }
}

/**
 * animating seekbar
 *
 * @param value
 *
 * @see animateValue
 */
fun SeekBar.animate(value: Int) {
    animateValue(progress, value) { progress = it }
}

/**
 * Converting list to arrayList
 * @return
 */
fun <T> List<T>.toArrayList(): ArrayList<T> {
    val data = ArrayList<T>()
    forEach { data.add(it) }
    return data
}

/**
 * function provideDatabase
 * providing in app database
 *
 * @param context
 *
 * @see Context
 * @see Room.databaseBuilder
 *
 * @see CoronaDatabase
 *
 * @return CoronaDatabase
 */
fun provideDatabase(context: Context) =
    Room
        .databaseBuilder(
            context,
            CoronaDatabase::class.java,
            "AntiCorona.db"
        )
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries().build()