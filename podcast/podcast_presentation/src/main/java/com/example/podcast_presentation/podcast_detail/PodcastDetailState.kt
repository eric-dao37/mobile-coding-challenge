package com.example.podcast_presentation.podcast_detail

import com.example.core.domain.ProgressBarState
import com.example.podcast_domain.model.Podcast

data class PodcastDetailState(
    val podcast: Podcast? = null,
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
)