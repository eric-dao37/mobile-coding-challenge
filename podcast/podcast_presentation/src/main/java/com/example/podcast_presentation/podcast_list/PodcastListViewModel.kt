package com.example.podcast_presentation.podcast_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.podcast_domain.model.Podcast
import com.example.podcast_domain.use_case.PodcastUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodcastListViewModel
@Inject constructor(
    private  val podcastUseCase: PodcastUseCases,
): ViewModel() {

    val podcastPagingDataFlow: Flow<PagingData<Podcast>> =
            podcastUseCase.getPodcastList().cachedIn(viewModelScope)

//    fun getPodcasts() {
//        viewModelScope.launch(Dispatchers.IO) {
//            podcastUseCase.getPodcastList().collect {
//                it.adapter
//            }
//        }
//    }

}