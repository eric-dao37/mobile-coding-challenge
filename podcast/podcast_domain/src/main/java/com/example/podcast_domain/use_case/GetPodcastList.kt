package com.example.podcast_domain.use_case

import androidx.paging.PagingData
import com.example.core.domain.DataState
import com.example.podcast_domain.model.Podcast
import com.example.podcast_domain.repository.PodcastRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetPodcastList (
    private val repository: PodcastRepository
) {

    operator fun invoke(): Flow<PagingData<Podcast>> {
        return repository.getPodcasts().flowOn(Dispatchers.IO)
    }

}
