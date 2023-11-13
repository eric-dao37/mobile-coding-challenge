package com.example.podcast_domain.use_case

import com.example.core.domain.DataState
import com.example.podcast_domain.model.Podcast
import com.example.podcast_domain.repository.PodcastRepository
import kotlinx.coroutines.flow.Flow

class UpdatePodcast(
    private val repository: PodcastRepository
) {

    suspend operator fun invoke(
        podcast: Podcast
    ) : Flow<DataState<Podcast>> {
        return repository.updatePodcast(podcast)
    }
}