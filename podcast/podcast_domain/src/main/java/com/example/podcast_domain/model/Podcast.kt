package com.example.podcast_domain.model

data class Podcast(
    val title: String,
    val thumbnail: String,
    val publisherName: String,
    val isFavourite: Boolean = false,
)
