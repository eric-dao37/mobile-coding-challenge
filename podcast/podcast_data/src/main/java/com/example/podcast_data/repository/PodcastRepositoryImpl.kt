package com.example.podcast_data.repository

import com.example.core.domain.DataState
import com.example.core.domain.ProgressBarState
import com.example.podcast_data.local.PodcastDao
import com.example.podcast_data.mapper.toDomainModel
import com.example.podcast_data.mapper.toEntity
import com.example.podcast_data.remote.PodcastApi
import com.example.podcast_data.remote.dto.PodcastDto
import com.example.podcast_domain.model.Podcast
import com.example.podcast_domain.repository.PodcastRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.Exception

class PodcastRepositoryImpl(
    private val dao: PodcastDao,
    private val api: PodcastApi,
) : PodcastRepository {
    override suspend fun getPodCasts(): Flow<DataState<List<Podcast>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val podcastDtoList: List<PodcastDto> = try {
                api.getPodcasts().podcasts.map {
                    it.podcast
                }
            } catch (e: Exception) {
                emit(DataState.Error(e.message?: "Unknown Error"))
                listOf()
            }

            // save the network data to database
            insertPodcastList(podcastDtoList)

            // return data from local database
            val podcasts = dao.getAllPodcast().map {
                it.toDomainModel()
            }
            emit(DataState.Data(podcasts))


        } catch (e: Exception) {
            emit(DataState.Error(e.message?: "Unknown Error"))
        }

        finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
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