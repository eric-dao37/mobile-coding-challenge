package com.example.podcast_domain.repository

import com.example.core.domain.DataState
import com.example.podcast_domain.model.Podcast
import kotlinx.coroutines.flow.Flow

interface PodcastRepository {

    suspend fun getPodCasts(): Flow<DataState<List<Podcast>>>

}