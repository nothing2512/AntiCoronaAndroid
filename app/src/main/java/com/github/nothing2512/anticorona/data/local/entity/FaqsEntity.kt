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
import com.github.nothing2512.anticorona.data.remote.response.FaqsResponse

/**
 * [FaqsEntity] class
 * @author Robet Atiq Maulana Rifqi
 * @see Entity
 *
 * @constructor [id], [question], [answer]
 */
@Entity
data class FaqsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val question: String,
    val answer: String
) {

    /**
     * function toResponse
     * convert FaqsEntity to CaseResponse
     *
     * @see FaqsResponse
     *
     * @return CaseResponse
     */
    fun toResponse() = FaqsResponse(question, answer)

    companion object {

        /**
         * function parse
         * parsing data from CaseEntity to FaqsEntity
         *
         * @param data
         *
         * @see FaqsResponse
         */
        @JvmStatic
        fun parse(data: FaqsResponse) = FaqsEntity(null, data.question, data.answer)
    }
}