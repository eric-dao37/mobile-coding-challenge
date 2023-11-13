package com.example.podcast_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PodcastEntity (
    @PrimaryKey val id: String,
    val title: String,
    val thumbnail: String,
    val publisherName: String,
    val description: String,
    val isFavourite: Boolean,
)