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
import com.example.core.domain.DataState
import com.example.core.domain.ProgressBarState
import com.example.podcast_domain.model.Podcast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext

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
        viewModelScope.launch(Dispatchers.IO) {
            podcastUseCase.getPodcast().collect { dataState ->
                handleDataState(dataState)
            }
        }
    }

    private fun handleDataState(dataState: DataState<List<Podcast>>) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                when(dataState){
                    is DataState.Loading -> {
                        state = state.copy(
                            progressBarState = dataState.progressBarState,
                        )
                    }
                    is DataState.Data -> {
                        state = state.copy(
                            podcastList = dataState.value
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
}