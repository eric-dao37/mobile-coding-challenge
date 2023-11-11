package com.example.podcast_presentation.podcast_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.example.core.R
import com.example.core.components.DefaultScreenUI
import com.example.core.util.UiEvent
import com.example.podcast_presentation.components.PodcastItem

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun PodcastListScreen(
    scaffoldState: ScaffoldState,
//    onNavigateToDetail: (detailId: String) -> Unit,
    viewModel: PodcastListViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
                else -> Unit
            }
        }
    }

    DefaultScreenUI(
        progressBarState = state.progressBarState,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Text(
                text = "Podcasts",
                style = MaterialTheme.typography.body2,
            )
            AnimatedVisibility(visible = state.podcastList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp)
                ) {
                    items(state.podcastList) { podcast ->
                        PodcastItem(
                            podcast = podcast,
                            onPodcastItemSelect = {},
                        )
                    }
                }
            }
        }
    }
}
