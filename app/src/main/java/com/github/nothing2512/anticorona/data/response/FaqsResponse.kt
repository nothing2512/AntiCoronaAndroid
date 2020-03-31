package com.github.nothing2512.anticorona.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FaqsResponse(
    val question: String,
    val answer: String
): Parcelable