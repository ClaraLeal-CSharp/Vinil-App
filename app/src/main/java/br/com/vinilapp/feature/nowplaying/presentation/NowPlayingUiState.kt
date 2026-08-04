package br.com.vinilapp.feature.nowplaying.presentation

import android.graphics.Bitmap

/** Estado de apresentação da reprodução atual. */
data class NowPlayingUiState(
    val title: String = "",
    val artist: String = "",
    val album: String = "",
    val source: String = "",
    val elapsedTime: String = "",
    val duration: String = "",
    val progress: Float = 0f,
    val albumArt: Bitmap? = null,
    val isPlaying: Boolean = false,
    val isAvailable: Boolean = false,
    val needsNotificationAccess: Boolean = false
)
