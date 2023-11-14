package com.example.podcast_presentation.podcast_list

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.core.util.UiEvent
import com.example.core.util.UiText
import com.example.podcast_domain.use_case.PodcastUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.core.domain.DataState
import com.example.podcast_domain.model.Podcast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext
import androidx.paging.cachedIn
import androidx.paging.map

@HiltViewModel
class PodcastListViewModel
@Inject constructor(
    private val podcastUseCase: PodcastUseCases,
): ViewModel() {

    var state by mutableStateOf(PodcastListState())
        private set
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val podcastPagingDataFlow: Flow<PagingData<Podcast>> =
            podcastUseCase.getPodcastList().cachedIn(viewModelScope)

    private fun fetchPodcastList() {
        viewModelScope.launch(Dispatchers.IO) {
            podcastUseCase.getPodcastList().collect{
                handlePagingState(it)
            }
        }
    }
    private fun handlePagingState(sate: PagingData<Podcast>) {
        viewModelScope.launch {
            sate

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
                            podcastList = dataState.data,
                            progressBarState = dataState.progressBarState,
                        )
                    }
                    is DataState.Error -> {
                        state = state.copy(
                            progressBarState = dataState.progressBarState,
                        )
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