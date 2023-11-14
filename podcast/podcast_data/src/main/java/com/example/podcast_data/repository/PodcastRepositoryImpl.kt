package com.example.podcast_data.repository

import com.example.core.domain.DataState
import com.example.core.domain.ProgressBarState
import com.example.podcast_data.local.PodcastDao
import com.example.podcast_data.local.entity.PodcastEntity
import com.example.podcast_data.mapper.toDomainModel
import com.example.podcast_data.mapper.toEntity
import com.example.podcast_data.remote.PodcastApi
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
                api.getPodcasts().podcasts.mapNotNull {
                    it.toEntity()
                }
            } catch (e: Exception) {
                emit(DataState.Error(e.message?: "Unknown Error"))
                listOf()
            }

            // save the network data to database
            dao.insertAllPodcasts(podcastEntityList)

            // return data from local database
            dao.getAllPodcast().collect{ list->
                 val podcasts = list.map {
                    it.toDomainModel()
                }
                emit(DataState.Data(podcasts))
            }

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
            dao.getPodcastDetail(id).collect { list ->
                val cachedPodcast = list.filter {
                    it.id == id
                }.map {
                    it.toDomainModel()
                }.firstOrNull() ?: throw Exception("That podcast does not exist in the cache.")

                emit(DataState.Data(cachedPodcast))
            }
        }catch (e: Exception){
            e.printStackTrace()
            emit(DataState.Error(e.message ?: "Unknown Error"))
        }
        finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }

    override suspend fun updatePodcast(podcast: Podcast) {
        try {
            //Update data
            dao.updatePodcast(podcast.toEntity())
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}