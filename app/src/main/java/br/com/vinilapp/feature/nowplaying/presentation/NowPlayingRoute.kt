package br.com.vinilapp.feature.nowplaying.presentation

import android.content.Intent
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun NowPlayingRoute(viewModel: NowPlayingViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NowPlayingScreen(
        uiState = uiState,
        onOpenNotificationAccessSettings = {
            context.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        },
        onPreviousClick = viewModel::onPreviousClick,
        onPlayPauseClick = viewModel::onPlayPauseClick,
        onNextClick = viewModel::onNextClick
    )
}
