package com.example.podcast_data.repository

import com.example.core.domain.DataState
import com.example.core.domain.ProgressBarState
import com.example.podcast_data.local.PodcastDao
import com.example.podcast_data.local.entity.PodcastEntity
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
    override suspend fun getPodcasts(): Flow<DataState<List<Podcast>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val podcastEntityList: List<PodcastEntity> = try {
                api.getPodcasts().podcasts.map {
                    it.toEntity()
                }
            } catch (e: Exception) {
                emit(DataState.Error(e.message?: "Unknown Error"))
                listOf()
            }

            // save the network data to database
            insertPodcastList(podcastEntityList)

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

    override suspend fun getPodcastDetail(id: String): Flow<DataState<Podcast>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            // emit data from network
            val cachedPodcast = dao.getPodcastDetail(id).filter {
                it.id == id
            }.map {
                it.toDomainModel()
            }.firstOrNull() ?: throw Exception("That podcast does not exist in the cache.")

            emit(DataState.Data(cachedPodcast))
        }catch (e: Exception){
            e.printStackTrace()
            emit(DataState.Error(e.message ?: "Unknown Error"))
        }
        finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }

    override suspend fun updatePodcast(podcast: Podcast): Flow<DataState<Podcast>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))
            //Update data
            dao.updatePodcast(podcast.toEntity())

            // emit data from network
            val cachedPodcast = dao.getPodcastDetail(podcast.id).filter {
                it.id == podcast.id
            }.map {
                it.toDomainModel()
            }.firstOrNull() ?: throw Exception("That podcast does not exist in the cache.")

            emit(DataState.Data(cachedPodcast))
        }catch (e: Exception){
            e.printStackTrace()
            emit(DataState.Error(e.message ?: "Unknown Error"))
        }
        finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }

    private suspend fun insertPodcastList(podcasts: List<PodcastEntity>) {
        for(podcast in podcasts){
            try {
                dao.insertPodcast(podcastEntity = podcast)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}