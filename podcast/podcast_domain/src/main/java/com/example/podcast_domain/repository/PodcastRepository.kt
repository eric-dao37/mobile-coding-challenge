package com.example.podcast_domain.repository

import com.example.podcast_domain.model.Podcast

interface PodcastRepository {

    suspend fun getPodCasts(): Result<List<Podcast>>

}