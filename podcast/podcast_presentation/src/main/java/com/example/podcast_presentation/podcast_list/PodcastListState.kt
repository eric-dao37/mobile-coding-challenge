package com.example.podcast_presentation.podcast_list

import com.example.core.domain.ProgressBarState
import com.example.podcast_domain.model.Podcast

data class PodcastListState(
    val podcastList: List<Podcast> = emptyList(),
    val currentPage: Int = 0,
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
)
