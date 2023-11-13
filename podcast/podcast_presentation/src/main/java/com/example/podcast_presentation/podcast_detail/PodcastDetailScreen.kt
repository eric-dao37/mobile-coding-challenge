package com.example.podcast_presentation.podcast_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.core.LocalSpacing
import com.example.core.Pink
import com.example.core.components.ToolbarScreenUI
import com.example.core.R
import com.example.core.util.UiEvent

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun PodcastDetailScreen (
    scaffoldState: ScaffoldState,
    navController: NavController,
    viewModel: PodcastDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val spacing = LocalSpacing.current
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
    ToolbarScreenUI(
        navController = navController,
        progressBarState = state.progressBarState,
    ) {
        state.podcast?.let { podcast ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                    .padding(spacing.spaceMedium),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = spacing.spaceSmall)
                        .align(alignment = Alignment.CenterHorizontally),
                    text = podcast.title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h3,
                )
                Text(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally),
                    text = podcast.publisherName,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.subtitle1,
                )
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(spacing.podcastImageSizeLarge),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = podcast.thumbnail,
                            builder = {
                                crossfade(true)
                                error(R.drawable.baseline_image)
                                fallback(R.drawable.baseline_image)
                            }
                        ),
                        contentDescription = podcast.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                            .clip(
                                RoundedCornerShape(spacing.roundedConnerMedium)
                            )
                    )
                }
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                Button(
                    onClick = {  },
                    modifier = Modifier.padding(
                        horizontal = spacing.spaceMedium,
                        vertical = spacing.spaceExtraSmall,
                    ),
                    shape = RoundedCornerShape(spacing.roundedConnerMedium),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Pink)
                ) {
                    Text(
                        text = if (podcast.isFavourite)
                            stringResource(id = R.string.favourited)
                        else stringResource(id = R.string.favourite),
                        color = Color.White,
                    )
                }

                Spacer(modifier = Modifier.height(spacing.spaceSmall))

                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .weight(
                            weight =1f,
                            fill = false
                        )
                ) {
                    Text(
                        text = podcast.description,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.padding(
                            horizontal = spacing.spaceMedium
                        )
                    )
                }
            }
        }
    }

}
