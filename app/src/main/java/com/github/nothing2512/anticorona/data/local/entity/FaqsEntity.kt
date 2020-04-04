package com.github.nothing2512.anticorona.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.nothing2512.anticorona.data.remote.response.FaqsResponse

@Entity
data class FaqsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val question: String,
    val answer: String
) {

    fun toResponse() = FaqsResponse(question, answer)

    companion object {

        fun parse(data: FaqsResponse) = FaqsEntity(null, data.question, data.answer)
    }
}