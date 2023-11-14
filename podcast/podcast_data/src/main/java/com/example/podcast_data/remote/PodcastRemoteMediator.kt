package com.example.podcast_data.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.podcast_data.local.PodcastDatabase
import com.example.podcast_data.local.entity.PodcastEntity
import com.example.podcast_data.mapper.toEntity
import java.lang.Exception
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PodcastRemoteMediator @Inject constructor(
    private val podcastDatabase: PodcastDatabase,
    private val podcastApi: PodcastApi,
) : RemoteMediator<Int, PodcastEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PodcastEntity>,
    ): MediatorResult {
        return try {
            val currentPage = state.anchorPosition ?: 1
            // MAKE API CALL
            val apiResponse = podcastApi.getPodcasts(
                page = currentPage,
            )

            Log.i("podcast Response", apiResponse.toString())
            // SAVE RESULTS AND NEXT OFFSET TO DATABASE
            podcastDatabase.withTransaction {
                podcastDatabase.podcastDao.insertAllPodcasts(
                    apiResponse.podcasts.map{ it.toEntity() }
                )
            }


            // CHECK IF END OF PAGINATION REACHED
            // Assume that if next page number is null
            MediatorResult.Success(endOfPaginationReached = apiResponse.nextPageNumber == null)
        } catch (e: Exception) {
            Log.i("podcast Response", e.toString())

            MediatorResult.Error(e)
        }
    }
}