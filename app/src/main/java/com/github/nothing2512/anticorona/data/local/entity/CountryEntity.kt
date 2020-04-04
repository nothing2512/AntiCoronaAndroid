package com.github.nothing2512.anticorona.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse

@Entity
data class CountryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val location: String,
    val cases: Int,
    val recovered: Int,
    val death: Int,
    val flag: String
) {

    fun toResponse() = CaseResponse(location, cases, recovered, death, flag)

    companion object {

        fun parse(data: CaseResponse) = CountryEntity(
            null,
            data.location ?: "",
            data.cases,
            data.recovered,
            data.death,
            data.flag ?: ""
        )
    }
}