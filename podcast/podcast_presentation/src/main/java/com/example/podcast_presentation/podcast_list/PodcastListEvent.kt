package com.example.podcast_presentation.podcast_list

import com.example.podcast_domain.model.Podcast

sealed class PodcastListEvent {
    data class OnPodcastDetailClick(val podcast: Podcast): PodcastListEvent()
    data class OnNextPageLoad(val page: Int): PodcastListEvent()
}