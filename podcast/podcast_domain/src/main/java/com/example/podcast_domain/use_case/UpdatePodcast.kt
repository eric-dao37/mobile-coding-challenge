package com.example.podcast_domain.use_case

import com.example.podcast_domain.model.Podcast
import com.example.podcast_domain.repository.PodcastRepository

class UpdatePodcast(
    private val repository: PodcastRepository
) {

    suspend operator fun invoke(
        podcast: Podcast
    ) {
        repository.updatePodcast(podcast)
    }
}