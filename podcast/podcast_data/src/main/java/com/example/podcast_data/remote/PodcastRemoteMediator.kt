package com.example.podcast_data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.podcast_data.local.PodcastDatabase
import com.example.podcast_data.local.entity.PodcastEntity
import com.example.podcast_data.local.entity.RemoteKeyEntity
import com.example.podcast_data.mapper.toEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PodcastRemoteMediator @Inject constructor(
    private val podcastDatabase: PodcastDatabase,
    private val podcastApi: PodcastApi,
) : RemoteMediator<Int, PodcastEntity>() {
    private val REMOTE_KEY_ID = "podcast"

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PodcastEntity>,
    ): MediatorResult {

        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    // RETRIEVE NEXT OFFSET FROM DATABASE
                    val remoteKey = podcastDatabase.remoteKeyDao.getById(REMOTE_KEY_ID)
                    if (remoteKey == null || remoteKey.nextPage == 0) // END OF PAGINATION REACHED
                        return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKey.nextPage ?: 0
                }
            }

            // MAKE API CALL
            val apiResponse = podcastApi.getPodcasts(
                page = page,
            )
            val results = apiResponse.podcasts
            val nextPage = apiResponse.nextPageNumber ?: 0

            // SAVE RESULTS AND NEXT OFFSET TO DATABASE
            podcastDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    // IF REFRESHING, CLEAR DATABASE FIRST
                    podcastDatabase.podcastDao.clearAll()
                    podcastDatabase.remoteKeyDao.deleteById(REMOTE_KEY_ID)
                }
                podcastDatabase.podcastDao.insertAllPodcasts(
                    results.map { it.toEntity() }
                )
                podcastDatabase.remoteKeyDao.insert(
                    RemoteKeyEntity(
                        id = REMOTE_KEY_ID,
                        nextPage = nextPage,
                    )
                )
            }

            // CHECK IF END OF PAGINATION REACHED
            // Assume that if next page number is null
            MediatorResult.Success(endOfPaginationReached = apiResponse.nextPageNumber == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}