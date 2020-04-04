package com.github.nothing2512.anticorona.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.nothing2512.anticorona.data.remote.response.NewsResponse

@Entity
data class NewsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val author: String,
    val title: String,
    val desc: String,
    val image: String,
    val url: String,
    val content: String
) {

    fun toResponse() = NewsResponse(author, title, desc, image, url, content)

    companion object {

        fun parse(data: NewsResponse) = NewsEntity(
            null,
            data.author,
            data.title,
            data.desc,
            data.image,
            data.url,
            data.content
        )
    }
}