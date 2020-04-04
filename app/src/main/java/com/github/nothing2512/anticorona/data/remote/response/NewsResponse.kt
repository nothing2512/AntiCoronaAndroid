package com.github.nothing2512.anticorona.data.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsResponse(
    val author: String,
    val title: String,
    val desc: String,
    val image: String,
    val url: String,
    val content: String
) : Parcelable