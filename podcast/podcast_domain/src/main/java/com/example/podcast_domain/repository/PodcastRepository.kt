package com.example.podcast_domain.repository

import androidx.paging.PagingData
import com.example.core.domain.DataState
import com.example.podcast_domain.model.Podcast
import kotlinx.coroutines.flow.Flow

interface PodcastRepository {

//    suspend fun getPodcasts(): Flow<DataState<List<Podcast>>>
    fun getPodcasts():Flow<PagingData<Podcast>>
    suspend fun getPodcastDetail(id: String): Flow<DataState<Podcast>>

    suspend fun updatePodcast(podcast: Podcast)

}