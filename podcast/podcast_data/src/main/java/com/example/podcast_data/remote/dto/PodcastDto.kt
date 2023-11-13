package com.example.podcast_data.remote.dto

import com.squareup.moshi.Json

data class PodcastDto(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "thumbnail")
    val thumbnail: String,
    @field:Json(name = "publisher")
    val publisherName: String,
    @field:Json(name = "description")
    val description: String
)
