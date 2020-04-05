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
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse

/**
 * [IndonesianEntity] class
 * @author Robet Atiq Maulana Rifqi
 * @see Entity
 *
 * @constructor [id], [location], [cases], [recovered], [death], [flag]
 */
@Entity
data class IndonesianEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val location: String,
    val cases: Int,
    val recovered: Int,
    val death: Int,
    val flag: String
) {

    /**
     * function toResponse
     * convert IndonesianEntity to CaseResponse
     *
     * @see CaseResponse
     *
     * @return CaseResponse
     */
    fun toResponse() = CaseResponse(location, cases, recovered, death, flag)

    companion object {

        /**
         * function parse
         * parsing data from CaseEntity to IndonesianEntity
         *
         * @param data
         *
         * @see CaseResponse
         */
        @JvmStatic
        fun parse(data: CaseResponse) = IndonesianEntity(
            null,
            data.location ?: "Indonesia",
            data.cases,
            data.recovered,
            data.death,
            data.flag ?: ""
        )
    }
}