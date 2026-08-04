package br.com.vinilapp.feature.nowplaying.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import br.com.vinilapp.R
import br.com.vinilapp.core.designsystem.component.VinylDisk
import br.com.vinilapp.core.designsystem.theme.VinilTheme

/** Interface principal mockada; ainda não há comunicação com APIs de mídia. */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NowPlayingScreen(uiState: NowPlayingUiState) {
    val colors = VinilTheme.colors
    val fonts = VinilTheme.fonts
    val sizes = VinilTheme.sizes
    val backgrounds = VinilTheme.backgrounds

    val displayState = uiState.copy(
        title = uiState.title.ifBlank { stringResource(R.string.now_playing_mock_song) },
        artist = uiState.artist.ifBlank { stringResource(R.string.now_playing_mock_artist) },
        source = uiState.source.ifBlank { stringResource(R.string.now_playing_mock_source) },
        elapsedTime = uiState.elapsedTime.ifBlank { stringResource(R.string.now_playing_mock_elapsed) },
        duration = uiState.duration.ifBlank { stringResource(R.string.now_playing_mock_duration) }
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(backgrounds.app),
        containerColor = colors.transparent,
        contentColor = colors.onBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(start = sizes.appBarTitleInset),
                        text = stringResource(R.string.now_playing_title),
                        style = fonts.title,
                        color = colors.onSurface
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.surface,
                    titleContentColor = colors.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = sizes.screenPadding),
            verticalArrangement = Arrangement.spacedBy(sizes.contentSpacing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VinylRecordStage(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(sizes.recordAreaWeight)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(sizes.controlsAreaWeight),
                verticalArrangement = Arrangement.spacedBy(
                    sizes.contentSpacing,
                    Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NowPlayingMetadata(uiState = displayState)
                PlaybackProgress(uiState = displayState)
                PlaybackControls(isPlaying = displayState.isPlaying)
            }
        }
    }
}

@Composable
private fun VinylRecordStage(modifier: Modifier = Modifier) {
    val discs = VinilTheme.discs

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val discSize = minOf(maxWidth, maxHeight, discs.maxSize)

        VinylDisk(
            modifier = Modifier.size(discSize),
            isRotating = true
        ) {
            MockAlbumArtwork()
        }
    }
}

@Composable
private fun MockAlbumArtwork(modifier: Modifier = Modifier) {
    val discs = VinilTheme.discs
    val controls = VinilTheme.controls
    val artworkDescription = stringResource(R.string.now_playing_album_art_description)

    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(controls.cornerShape)
            .background(discs.artworkBackground)
            .semantics { contentDescription = artworkDescription }
    )
}

@Composable
private fun NowPlayingMetadata(uiState: NowPlayingUiState) {
    val fonts = VinilTheme.fonts
    val colors = VinilTheme.colors
    val sizes = VinilTheme.sizes

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(sizes.compactSpacing),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = uiState.title,
            style = fonts.headline,
            color = colors.onBackground,
            textAlign = TextAlign.Center
        )
        Text(
            text = uiState.artist,
            style = fonts.body,
            color = colors.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Text(
            text = uiState.source,
            style = fonts.caption,
            color = colors.secondary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun PlaybackProgress(uiState: NowPlayingUiState) {
    val controls = VinilTheme.controls
    val fonts = VinilTheme.fonts
    val colors = VinilTheme.colors
    val sizes = VinilTheme.sizes

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(sizes.compactSpacing)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(sizes.progressHeight)
                .clip(controls.cornerShape)
                .background(controls.progressTrackColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(uiState.progress.coerceIn(PROGRESS_START, PROGRESS_END))
                    .fillMaxHeight()
                    .background(controls.progressActiveTrackColor)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = uiState.elapsedTime,
                style = fonts.caption,
                color = colors.onSurfaceVariant
            )
            Text(
                text = uiState.duration,
                style = fonts.caption,
                color = colors.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun PlaybackControls(isPlaying: Boolean) {
    val sizes = VinilTheme.sizes

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            sizes.controlsSpacing,
            Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlaybackButton(
            modifier = Modifier.weight(sizes.secondaryControlWeight),
            label = stringResource(R.string.now_playing_previous),
            isPrimary = false
        )
        PlaybackButton(
            modifier = Modifier
                .weight(sizes.primaryControlWeight),
            label = stringResource(
                if (isPlaying) {
                    R.string.now_playing_pause
                } else {
                    R.string.now_playing_play
                }
            ),
            isPrimary = true
        )
        PlaybackButton(
            modifier = Modifier.weight(sizes.secondaryControlWeight),
            label = stringResource(R.string.now_playing_next),
            isPrimary = false
        )
    }
}

@Composable
private fun PlaybackButton(label: String, isPrimary: Boolean, modifier: Modifier = Modifier) {
    val controls = VinilTheme.controls
    val fonts = VinilTheme.fonts

    Button(
        modifier = modifier.height(
            if (isPrimary) {
                controls.primaryTouchTarget
            } else {
                controls.minTouchTarget
            }
        ),
        onClick = {},
        shape = controls.cornerShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPrimary) controls.containerColor else controls.secondaryContainerColor,
            contentColor = if (isPrimary) controls.contentColor else controls.secondaryContentColor,
            disabledContainerColor = controls.disabledContainerColor,
            disabledContentColor = controls.disabledContentColor
        )
    ) {
        Text(
            text = label,
            style = fonts.control,
            color = if (isPrimary) controls.contentColor else controls.secondaryContentColor,
            maxLines = CONTROL_LABEL_MAX_LINES
        )
    }
}

private const val PROGRESS_START = 0f
private const val PROGRESS_END = 1f
private const val CONTROL_LABEL_MAX_LINES = 1
