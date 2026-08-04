package br.com.vinilapp.feature.nowplaying.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NowPlayingRoute(viewModel: NowPlayingViewModel = viewModel()) {
    viewModel.uiState.collectAsStateWithLifecycle()
    NowPlayingScreen()
}
