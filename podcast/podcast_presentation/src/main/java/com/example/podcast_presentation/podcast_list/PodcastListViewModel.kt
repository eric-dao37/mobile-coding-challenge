package com.example.podcast_presentation.podcast_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.podcast_domain.model.Podcast
import com.example.podcast_domain.use_case.PodcastUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PodcastListViewModel
@Inject constructor(
    podcastUseCase: PodcastUseCases,
): ViewModel() {

    val podcastPagingDataFlow: Flow<PagingData<Podcast>> =
            podcastUseCase.getPodcastList().cachedIn(viewModelScope)

}