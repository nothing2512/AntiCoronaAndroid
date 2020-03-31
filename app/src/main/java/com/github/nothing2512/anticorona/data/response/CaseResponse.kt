package com.github.nothing2512.anticorona.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CaseResponse(
    val location: String?,
    val cases: Int,
    val recovered: Int,
    val death: Int,
    val flag: String?
): Parcelable