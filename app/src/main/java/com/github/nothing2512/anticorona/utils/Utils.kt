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

package com.github.nothing2512.anticorona.utils

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.github.nothing2512.anticorona.BuildConfig
import com.github.nothing2512.anticorona.data.local.CoronaDatabase
import com.github.nothing2512.anticorona.data.remote.Services
import com.github.nothing2512.anticorona.data.remote.adapter.CallAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

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

/**
 * function provideServices
 * providing remote services
 *
 * @see Retrofit.Builder
 * @see CallAdapterFactory
 * @see CallAdapterFactory
 * @see trustAllCaClient
 * @see Services
 *
 * @return Services
 */
fun provideServices(): Services = Retrofit.Builder()
    .addCallAdapterFactory(CallAdapterFactory())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BuildConfig.BASE_URL)
    .client(trustAllCaClient())
    .build()
    .create(Services::class.java)

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
 * showing toast from another class
 *
 * @param context
 * @param msg
 *
 * @see Toast.makeText
 */
fun toast(context: Context?, msg: String?) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

/**
 * Idling espressoIdlingResource
 *
 * @param onHandle
 *
 * @see EspressoIdlingResource.handle
 */
fun <T> idle(onHandle: () -> LiveData<T>) = EspressoIdlingResource.handle(onHandle)

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
 * Send Email
 *
 * @param context
 * @param email
 *
 * @see Intent
 */
fun openEmail(context: Context?, email: String) {
    try {
        Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            type = "text/html"
            `package` = "com.google.android.gm"
            context?.startActivity(this)
        }
    } catch (_: Exception) {
        toast(context, "Gmail not installed")
    }
}

/**
 * Send Whatsapp
 *
 * @param context
 * @param number
 *
 * @see Intent
 */
fun openWhatsapp(context: Context?, number: String) {
    Intent(Intent.ACTION_SEND).apply {
        putExtra(Constants.EXTRA_NUMBER, "$number@s.whatsapp.net")
        putExtra(Intent.EXTRA_TEXT, "Hi")
        type = "text/plain"
        try {
            `package` = "com.whatsapp"
            context?.startActivity(this)
        } catch (_: java.lang.Exception) {
            try {
                `package` = "com.whatsapp.w4b"
                context?.startActivity(this)
            } catch (_: Exception) {
                toast(context, "Whatsapp not installed")
            }
        }
    }
}

/**
 * open dial phone
 *
 * @param context
 * @param number
 *
 * @see Intent
 */
fun openDial(context: Context?, number: String) {
    Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number")).apply {
        context?.startActivity(this)
    }
}

/**
 * Trsuting all ca client
 * @see X509TrustManager
 * @see X509Certificate
 * @see SSLContext
 * @see OkHttpClient.Builder
 */
fun trustAllCaClient() = try {
    val ca = object : X509TrustManager {
        @SuppressLint("TrustAllX509TrustManager")
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        @SuppressLint("TrustAllX509TrustManager")
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()

    }

    val ctx = SSLContext.getInstance("SSL")
    ctx.init(null, arrayOf(ca), SecureRandom())

    OkHttpClient.Builder()
        .sslSocketFactory(ctx.socketFactory, ca as X509TrustManager)
        .hostnameVerifier { _, _ -> true }
        .build()
} catch (e: Exception) {
    throw RuntimeException(e)
}