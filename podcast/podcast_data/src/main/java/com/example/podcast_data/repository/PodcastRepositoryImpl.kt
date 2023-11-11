package com.example.podcast_data.repository

import android.util.Log
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
                Log.i("Podcast Err", ("1" + e.message) ?: "none")

                return Result.failure(e)
            }
            // save the network data to database
            insertPodcastList(podcastDtoList)

            // return data from local database
            val podcasts = dao.getAllPodcast().map {
                it.toDomainModel()
            }
            return Result.success(podcasts)

        } catch (e: Exception) {
            Log.i("Podcast Err", ("2" + e.message))
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