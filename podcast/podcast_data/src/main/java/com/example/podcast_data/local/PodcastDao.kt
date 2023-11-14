package com.example.podcast_data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.podcast_data.local.entity.PodcastEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PodcastDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPodcast(podcastEntity: PodcastEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllPodcasts(podcastEntityList: List<PodcastEntity>)

    @Query(
        """
            SELECT * FROM podcastentity
            WHERE id = :podcastId
        """)
    fun getPodcastDetail(podcastId: String): Flow<List<PodcastEntity>>

    @Query(
        """
            SELECT * FROM podcastentity
        """)
    fun getAllPodcast(): Flow<List<PodcastEntity>>

    @Update
    fun updatePodcast(postCastEntity: PodcastEntity)

    @Query("SELECT * FROM podcastentity")
    fun pagingSource(): PagingSource<Int, PodcastEntity>
}