package com.example.podcast_presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.core.LocalSpacing
import com.example.core.Pink
import com.example.core.R
import com.example.podcast_domain.model.Podcast

@ExperimentalCoilApi
@Composable
fun PodcastItem(
    podcast: Podcast,
    onPodcastItemSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .clickable {
                onPodcastItemSelect(podcast.id)
            }
            .padding(
                horizontal = spacing.spaceMedium,
                vertical = spacing.spaceSmall
            )
    ) {
        Row(
            modifier = modifier
                .clip(
                    RoundedCornerShape(
                        topStart = spacing.roundedConnerMedium,
                        bottomStart = spacing.roundedConnerMedium,
                    )
                )
                .height(spacing.podcastItemHeight),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
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
            Spacer(modifier = Modifier.width(spacing.spaceMedium))
            Column(
                modifier = Modifier
                    .align(
                        alignment = Alignment.Top
                    )
                    .weight(1f)
                    .padding(top = spacing.spaceExtraSmall)
            ) {
                Text(
                    text = podcast.title,
                    style = MaterialTheme.typography.h4,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = podcast.publisherName,
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                if (podcast.isFavourite)
                    Text(
                        text = stringResource(R.string.favourited),
                        style = MaterialTheme.typography.body2,
                        color = Pink,
                    )
            }
        }
    }
}