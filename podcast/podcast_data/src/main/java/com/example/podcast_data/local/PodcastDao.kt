package com.example.podcast_data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.podcast_data.local.entity.PodcastEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PodcastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPodcast(podcastEntity: PodcastEntity)

    @Query(
        """
            SELECT *
            FROM podcastentity
            WHERE id = :podcastId
        """)
    fun getPodcastDetail(podcastId: String): List<PodcastEntity>

    @Query(
        """
            SELECT *
            FROM podcastentity
        """)
    fun getAllPodcast(): List<PodcastEntity>

    @Update
    fun updatePodcast(postCastEntity: PodcastEntity)
}