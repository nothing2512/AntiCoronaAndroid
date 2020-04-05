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

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * [NewsResponse] class
 * @author Robet Atiq Maulana Rifqi
 *
 * @constructor [author], [title], [desc], [image], [url], [content]
 *
 * @see Parcelize
 * @see Parcelable
 */
@Parcelize
data class NewsResponse(
    val author: String,
    val title: String,
    val desc: String,
    val image: String,
    val url: String,
    val content: String
) : Parcelable