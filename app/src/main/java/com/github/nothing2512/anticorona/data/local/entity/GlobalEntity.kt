package com.github.nothing2512.anticorona.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.nothing2512.anticorona.data.remote.response.CaseResponse

@Entity
data class GlobalEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val cases: Int,
    val recovered: Int,
    val death: Int
) {

    fun toResponse() = CaseResponse(null, cases, recovered, death, null)

    companion object {

        fun parse(data: CaseResponse) = GlobalEntity(
            null,
            data.cases,
            data.recovered,
            data.death
        )
    }
}