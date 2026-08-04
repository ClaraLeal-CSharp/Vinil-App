package br.com.vinilapp.feature.nowplaying.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.vinilapp.domain.model.NowPlayingState
import br.com.vinilapp.domain.model.PlaybackCommand
import br.com.vinilapp.domain.usecase.ControlPlaybackUseCase
import br.com.vinilapp.domain.usecase.ObserveNowPlayingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    observeNowPlayingUseCase: ObserveNowPlayingUseCase,
    private val controlPlaybackUseCase: ControlPlaybackUseCase
) : ViewModel() {
    private val mutableUiState = MutableStateFlow(NowPlayingUiState())

    val uiState: StateFlow<NowPlayingUiState> = mutableUiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeNowPlayingUseCase().collect { nowPlayingState ->
                mutableUiState.value = nowPlayingState.toUiState()
            }
        }
    }

    fun onPreviousClick() {
        sendPlaybackCommand(PlaybackCommand.Previous)
    }

    fun onPlayPauseClick() {
        sendPlaybackCommand(PlaybackCommand.PlayPause)
    }

    fun onNextClick() {
        sendPlaybackCommand(PlaybackCommand.Next)
    }

    private fun sendPlaybackCommand(command: PlaybackCommand) {
        viewModelScope.launch {
            controlPlaybackUseCase(command)
        }
    }
}

private fun NowPlayingState.toUiState(): NowPlayingUiState = when (this) {
    NowPlayingState.PermissionRequired -> NowPlayingUiState(needsNotificationAccess = true)
    NowPlayingState.Unavailable -> NowPlayingUiState()
    is NowPlayingState.Available -> NowPlayingUiState(
        title = title,
        artist = artist,
        album = album,
        source = sourceAppName.ifBlank { sourcePackageName },
        elapsedTime = positionMillis.toPlaybackTime(),
        duration = durationMillis.toPlaybackTime(),
        progress = if (durationMillis > 0L) {
            positionMillis.toFloat() / durationMillis.toFloat()
        } else {
            0f
        },
        albumArt = albumArt,
        isPlaying = isPlaying,
        isAvailable = true
    )
}

private fun Long.toPlaybackTime(): String {
    val totalSeconds = (this / MILLIS_PER_SECOND).coerceAtLeast(0L)
    val minutes = totalSeconds / SECONDS_PER_MINUTE
    val seconds = totalSeconds % SECONDS_PER_MINUTE

    return "$minutes:${seconds.toString().padStart(TIME_SECOND_DIGITS, TIME_SECOND_PAD)}"
}

private const val MILLIS_PER_SECOND = 1_000L
private const val SECONDS_PER_MINUTE = 60L
private const val TIME_SECOND_DIGITS = 2
private const val TIME_SECOND_PAD = '0'
