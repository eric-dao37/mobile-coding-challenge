package com.example.podcast_domain.use_case

data class PodcastUseCases (
    val getPodcastList: GetPodcastList,
    val getPodcastDetail: GetPodcastDetail,
    val updatePodcast: UpdatePodcast,
)