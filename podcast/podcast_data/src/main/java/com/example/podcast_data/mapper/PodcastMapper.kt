package com.example.podcast_data.mapper

import com.example.podcast_data.local.entity.PodcastEntity
import com.example.podcast_data.remote.dto.PodcastDto
import com.example.podcast_domain.model.Podcast

fun PodcastDto.toEntity(): PodcastEntity {
    return PodcastEntity(
        id = id,
        title = title,
        thumbnail = thumbnail,
        publisherName = publisherName,
        description = description,
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