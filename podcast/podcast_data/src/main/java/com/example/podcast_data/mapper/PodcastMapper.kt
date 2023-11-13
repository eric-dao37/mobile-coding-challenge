package com.example.podcast_data.mapper

import com.example.podcast_data.local.entity.PodcastEntity
import com.example.podcast_data.remote.dto.PodcastDto
import com.example.podcast_data.remote.dto.PodcastObjectDto
import com.example.podcast_domain.model.Podcast


fun PodcastObjectDto.toEntity(): PodcastEntity {
    val podcast = podcast
    val description = description
    return PodcastEntity(
        id = podcast.id,
        title = podcast.title,
        thumbnail = podcast.thumbnail,
        publisherName = podcast.publisherName,
        description = description,
        isFavourite = false
    )
}

fun Podcast.toEntity(): PodcastEntity {
    return PodcastEntity(
        id = id,
        title = title,
        thumbnail = thumbnail,
        publisherName = publisherName,
        description =description,
        isFavourite = isFavourite
    )
}

fun PodcastEntity.toDomainModel(): Podcast {
    return Podcast(
        id = id,
        title = title,
        thumbnail = thumbnail,
        publisherName = publisherName,
        description =description,
        isFavourite = isFavourite
    )
}