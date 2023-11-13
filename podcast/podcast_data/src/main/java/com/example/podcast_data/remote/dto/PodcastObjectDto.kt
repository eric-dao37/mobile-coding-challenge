package com.example.podcast_data.remote.dto

import com.squareup.moshi.Json

data class PodcastObjectDto (
    @field:Json(name = "podcast")
    val podcast: PodcastDto,
    @field:Json(name = "description_original")
    val description: String
)