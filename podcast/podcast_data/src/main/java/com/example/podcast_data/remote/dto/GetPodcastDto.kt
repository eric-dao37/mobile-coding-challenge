package com.example.podcast_data.remote.dto

import com.squareup.moshi.Json

data class GetPodcastDto (
    @field:Json(name = "podcasts")
    val podcasts: List<PodcastDto>
)