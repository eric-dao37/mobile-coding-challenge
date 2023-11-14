package com.example.podcast_presentation.podcast_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.annotation.ExperimentalCoilApi
import com.example.core.LocalSpacing
import com.example.core.R
import com.example.core.components.DefaultScreenUI
import com.example.core.domain.ProgressBarState
import com.example.podcast_domain.model.Podcast
import com.example.podcast_presentation.components.PodcastItem

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun PodcastListScreen(
    onNavigateToDetail: (podcastIdId: String) -> Unit,
    viewModel: PodcastListViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val podcastPagingItems: LazyPagingItems<Podcast> = viewModel.podcastPagingDataFlow.collectAsLazyPagingItems()

    DefaultScreenUI(
        progressBarState = if (podcastPagingItems.loadState.refresh is LoadState.Loading)
            ProgressBarState.Loading else ProgressBarState.Idle,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            Text(
                text = stringResource(R.string.podcasts),
                style = MaterialTheme.typography.h2,
                modifier = Modifier
                    .padding(
                        horizontal = spacing.spaceMedium,
                        vertical = spacing.spaceSmall,
                    )

            )
            AnimatedVisibility(visible = podcastPagingItems.itemCount > 0) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = spacing.spaceMedium)
                ) {
                    items(
                        count = podcastPagingItems.itemCount,
                        key = podcastPagingItems.itemKey{it.id}
                    ) { index ->
                        val podcast = podcastPagingItems[index]
                        if (podcast != null)
                            PodcastItem(
                                podcast = podcast,
                                onPodcastItemSelect = onNavigateToDetail,
                            )
                    }
                    item {
                        if (podcastPagingItems.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }
        }
    }
}
