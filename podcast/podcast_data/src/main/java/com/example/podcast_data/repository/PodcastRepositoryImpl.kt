package com.example.podcast_data.repository

import com.example.podcast_data.local.PodcastDao
import com.example.podcast_data.mapper.toDomainModel
import com.example.podcast_data.mapper.toEntity
import com.example.podcast_data.remote.PodcastApi
import com.example.podcast_data.remote.dto.PodcastDto
import com.example.podcast_domain.model.Podcast
import com.example.podcast_domain.repository.PodcastRepository
import kotlin.Exception

class PodcastRepositoryImpl(
    private val dao: PodcastDao,
    private val api: PodcastApi,
) : PodcastRepository {
    override suspend fun getPodCasts(): Result<List<Podcast>> {
        try {
            val podcastDtoList: List<PodcastDto> = try {
                api.getPodcasts().podcasts.map {
                    it.podcast
                }
            } catch (e: Exception) {
                return Result.failure(e)
            }
            // cache the network data
            insertPodcastList(podcastDtoList)

            // return data from cache
            val podcasts = dao.getAllPodcast()
            return Result.success(
                podcasts.map {
                    it.toDomainModel()
                }
            )
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    private suspend fun insertPodcastList(podcasts: List<PodcastDto>) {
        for(podcast in podcasts){
            try {
                dao.insertPodcast(podcastEntity = podcast.toEntity())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}