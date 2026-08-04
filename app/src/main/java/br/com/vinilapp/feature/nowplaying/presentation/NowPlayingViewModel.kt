package br.com.vinilapp.feature.nowplaying.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** ViewModel inicial que delimita a camada de apresentação do recurso. */
class NowPlayingViewModel : ViewModel() {
    private val mutableUiState = MutableStateFlow(NowPlayingUiState)

    val uiState: StateFlow<NowPlayingUiState> = mutableUiState.asStateFlow()
}
