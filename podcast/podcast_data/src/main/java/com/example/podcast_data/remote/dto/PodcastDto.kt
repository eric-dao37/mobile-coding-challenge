package com.example.podcast_data.remote.dto

import com.squareup.moshi.Json

data class PodcastDto(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "title_original")
    val title: String,
    @field:Json(name = "thumbnail")
    val thumbnail: String,
    @field:Json(name = "publisher_original")
    val publisherName: String,
)
