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

package com.github.nothing2512.anticorona.data.remote.response

import android.content.Context
import android.os.Parcelable
import com.github.mikephil.charting.data.PieEntry
import com.github.nothing2512.anticorona.R
import kotlinx.android.parcel.Parcelize

/**
 * [CaseResponse] class
 * @author Robet Atiq Maulana Rifqi
 *
 * @constructor [location], [cases], [recovered], [death], [flag]
 *
 * @see Parcelize
 * @see Parcelable
 */
@Parcelize
data class CaseResponse(
    val location: String?,
    val cases: Int,
    val recovered: Int,
    val death: Int,
    val flag: String?
) : Parcelable {

    /**
     * Get pie entry
     * @param context
     * @see PieEntry
     * @return
     */
    fun toEntry(context: Context?) = listOf(
        PieEntry(cases.toFloat(), context?.getString(R.string.cases)),
        PieEntry(recovered.toFloat(), context?.getString(R.string.recovered)),
        PieEntry(death.toFloat(), context?.getString(R.string.death))
    )
}