package com.example.podcast_domain.use_case

import com.example.podcast_domain.model.Podcast
import com.example.podcast_domain.repository.PodcastRepository

class GetPodcast (
    private val repository: PodcastRepository
) {

    suspend operator fun invoke(): Result<List<Podcast>> {
        return repository.getPodCasts()
    }

}
