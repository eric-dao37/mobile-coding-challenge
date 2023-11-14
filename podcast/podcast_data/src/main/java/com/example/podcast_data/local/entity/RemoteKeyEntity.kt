package com.example.podcast_data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeyEntity(
    @PrimaryKey val id: String,
    val nextPage: Int?,
)