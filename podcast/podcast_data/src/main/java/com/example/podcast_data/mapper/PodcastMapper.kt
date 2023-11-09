package com.example.podcast_data.mapper

import com.example.podcast_data.remote.dto.PodcastDto
import com.example.podcast_domain.model.Podcast

fun PodcastDto.toDomainModel(): Podcast {
    return Podcast(
        title = title,
        thumbnail = thumbnail,
        publisherName = publisherName,
    )
}