package com.github.nothing2512.anticorona.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse

@Entity
data class ProvinceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val location: String,
    val cases: Int,
    val recovered: Int,
    val death: Int
) {

    fun toResponse() = CaseResponse(location, cases, recovered, death, null)

    companion object {

        fun parse(data: CaseResponse) = ProvinceEntity(
            null,
            data.location ?: "",
            data.cases,
            data.recovered,
            data.death
        )
    }
}