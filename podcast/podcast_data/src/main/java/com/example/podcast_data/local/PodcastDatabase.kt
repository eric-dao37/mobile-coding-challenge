package com.example.podcast_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.podcast_data.local.entity.PodcastEntity

@Database(
    entities = [PodcastEntity::class, ],
    version = 1
)
abstract class PodcastDatabase: RoomDatabase() {
    abstract val podcastDao: PodcastDao
}
