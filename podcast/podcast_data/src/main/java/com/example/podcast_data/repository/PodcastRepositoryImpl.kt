package com.example.podcast_data.repository

import com.example.podcast_data.mapper.toDomainModel
import com.example.podcast_data.remote.PodcastApi
import com.example.podcast_domain.model.Podcast
import com.example.podcast_domain.repository.PodcastRepository
import java.lang.Exception

class PodcastRepositoryImpl(
    private val api: PodcastApi,
) : PodcastRepository {
    override suspend fun getPodCasts(): Result<List<Podcast>> {
        return try {
            val getPodcastDto = api.getPodcasts()
            Result.success(
                getPodcastDto.podcasts.map {
                    it.podcast.toDomainModel()
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}