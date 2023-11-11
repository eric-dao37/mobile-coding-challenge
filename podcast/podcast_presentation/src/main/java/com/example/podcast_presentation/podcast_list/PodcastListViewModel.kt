package com.example.podcast_presentation.podcast_list

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.UiEvent
import com.example.core.util.UiText
import com.example.podcast_domain.use_case.PodcastUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.core.R
import com.example.core.domain.ProgressBarState
import kotlinx.coroutines.flow.receiveAsFlow

@HiltViewModel
class PodcastListViewModel
@Inject constructor(
    private val podcastUseCase: PodcastUseCases,
): ViewModel() {

    var state by mutableStateOf(PodcastListState())
        private set
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        fetchPodcastList()
    }

    private fun fetchPodcastList() {
        viewModelScope.launch {
            state = state.copy(
                progressBarState = ProgressBarState.Loading,
                podcastList = emptyList()
            )
            podcastUseCase
                .getPodcast()
                .onSuccess {
                    state = state.copy(
                        podcastList = it,
                        progressBarState = ProgressBarState.Idle,
                    )
                }
                .onFailure {
                    state = state.copy(
                        progressBarState = ProgressBarState.Idle,
                    )
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(R.string.error_something_went_wrong)
                        )
                    )
                }
        }
    }
}