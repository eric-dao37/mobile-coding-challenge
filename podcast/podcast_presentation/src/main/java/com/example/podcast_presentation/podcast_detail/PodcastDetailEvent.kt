package com.example.podcast_presentation.podcast_detail

sealed class PodcastDetailEvent {
    data class GetPodcastFromCache(
        val podcastId: String,
    ): PodcastDetailEvent()

    object OnFavouriteClick : PodcastDetailEvent()
}