package com.example.podcast_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.podcast_data.local.entity.PodcastEntity
import com.example.podcast_data.local.entity.RemoteKeyDao
import com.example.podcast_data.local.entity.RemoteKeyEntity

@Database(
    entities = [PodcastEntity::class, RemoteKeyEntity::class,],
    version = 1,
    exportSchema = false,
)
abstract class PodcastDatabase: RoomDatabase() {
    abstract val podcastDao: PodcastDao
    abstract val remoteKeyDao: RemoteKeyDao
}
