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

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.api.load
import coil.request.LoadRequestBuilder
import com.facebook.shimmer.ShimmerFrameLayout
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.nothing2512.anticorona.R
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
fun Activity.toast(msg: String?) = toast(applicationContext, msg)

/**
 * showing toast from fragment
 *
 * @param msg
 *
 * @see Toast.makeText
 */
fun Fragment.toast(msg: String?) = toast(activity?.applicationContext, msg)

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
 * Set pie chart data
 *
 * @param data
 *
 * @see PieChart
 */
fun PieChart.setup(data: List<PieEntry>) {

    /**
     * Get chart color list
     * @see ContextCompat.getColor
     */
    val colors = listOf(
        ContextCompat.getColor(context, R.color.cases),
        ContextCompat.getColor(context, R.color.recovered),
        ContextCompat.getColor(context, R.color.death)
    )

    /**
     * Create pie data set instance
     * Set color of chart
     *
     * @see PieDataSet
     */
    val pieDataSet = PieDataSet(data, "")
    pieDataSet.colors = colors

    /**
     * Setup PieChart
     *
     * @see PieChart.setData
     * @see PieChart.setUsePercentValues
     * @see PieChart.invalidate
     */
    setData(PieData(pieDataSet))
    setUsePercentValues(true)
    invalidate()
}

/**
 * Open Whatsapp
 * @see openWhatsapp
 */
fun Fragment.openWhatsapp(number: String) = openWhatsapp(activity?.applicationContext, number)

/**
 * Open email
 * @see openEmail
 */
fun Fragment.openEmail(email: String) = openEmail(activity?.applicationContext, email)

/**
 * Open dial
 * @see openDial
 */
fun Fragment.openDial(number: String) = openDial(activity?.applicationContext, number)