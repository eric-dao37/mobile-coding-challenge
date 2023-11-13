package com.example.podcast_presentation.podcast_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.DataState
import com.example.core.util.UiEvent
import com.example.core.util.UiText
import com.example.podcast_domain.model.Podcast
import com.example.podcast_domain.use_case.PodcastUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodcastDetailViewModel
@Inject constructor(
    private val podcastUseCase: PodcastUseCases,
    savedStateHandle: SavedStateHandle,
    ): ViewModel() {
    var state by mutableStateOf(PodcastDetailState())
        private set
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        savedStateHandle.get<String>("podcastId")?.let { podcastId ->
            onEvent(PodcastDetailEvent.GetPodcastFromCache(podcastId))
        }
    }

    fun onEvent(event: PodcastDetailEvent) {
        when(event) {
            is PodcastDetailEvent.GetPodcastFromCache -> {
                getPodcast(event.podcastId)
            }

            is PodcastDetailEvent.OnFavouriteClick -> {
                val podcast = state.podcast
                val updatedPodcast = podcast?.copy(
                    isFavourite = podcast.isFavourite.not()
                )
                state = state.copy(
                    podcast = updatedPodcast
                )
            }
        }
    }

    private fun getPodcast(podcastId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            podcastUseCase.getPodcastDetail(podcastId).collect(::handleDataState)
        }
    }

    private fun handleDataState(dataState: DataState<Podcast>) {
        viewModelScope.launch {
            when (dataState) {
                is DataState.Loading -> {
                    state = state.copy(
                        progressBarState = dataState.progressBarState
                    )
                }

                is DataState.Data -> {
                    state = state.copy(
                        podcast = dataState.value
                    )
                }

                is DataState.Error -> {
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            UiText.DynamicString(dataState.message)
                        )
                    )
                }
            }
        }
    }
}