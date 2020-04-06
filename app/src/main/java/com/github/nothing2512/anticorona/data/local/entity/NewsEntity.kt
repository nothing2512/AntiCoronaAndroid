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

package com.github.nothing2512.anticorona.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.nothing2512.anticorona.data.remote.response.NewsResponse

/**
 * [NewsEntity] class
 * @author Robet Atiq Maulana Rifqi
 * @see Entity
 *
 * @constructor [id], [author], [title], [image], [url]
 */
@Entity
data class NewsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val author: String,
    val title: String,
    val image: String,
    val url: String
) {

    /**
     * function toResponse
     * convert NewsEntity to NewsResponse
     *
     * @see NewsResponse
     *
     * @return CaseResponse
     */
    fun toResponse() = NewsResponse(author, title, image, url)

    companion object {

        /**
         * function parse
         * parsing data from NewsResponse to NewsEntity
         *
         * @param data
         *
         * @see NewsResponse
         */
        @JvmStatic
        fun parse(data: NewsResponse) = NewsEntity(
            null,
            data.author,
            data.title,
            data.image,
            data.url
        )
    }
}