package com.example.podcast_domain.use_case

import com.example.core.domain.DataState
import com.example.podcast_domain.model.Podcast
import com.example.podcast_domain.repository.PodcastRepository
import kotlinx.coroutines.flow.Flow

class GetPodcastDetail(
    private val repository: PodcastRepository
) {
    suspend operator fun invoke(
        podcastId: String
    ) : Flow<DataState<Podcast>>{
        return repository.getPodcastDetail(podcastId)
    }
}