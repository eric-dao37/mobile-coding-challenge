package com.example.podcast_domain.use_case

import com.example.core.domain.DataState
import com.example.podcast_domain.model.Podcast
import com.example.podcast_domain.repository.PodcastRepository
import kotlinx.coroutines.flow.Flow

class GetPodcastList (
    private val repository: PodcastRepository
) {

    suspend operator fun invoke(): Flow<DataState<List<Podcast>>> {
        return repository.getPodcasts()
    }

}
