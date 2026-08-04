package br.com.vinilapp.feature.nowplaying.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun NowPlayingRoute(viewModel: NowPlayingViewModel = hiltViewModel()) {
    viewModel.uiState.collectAsStateWithLifecycle()
    NowPlayingScreen()
}
