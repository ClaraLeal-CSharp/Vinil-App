package br.com.vinilapp.domain.model

import android.graphics.Bitmap

/** Estado de reprodução atual exposto pelas fontes oficiais de mídia do Android. */
sealed interface NowPlayingState {
    data object Unavailable : NowPlayingState

    data class Available(
        val title: String,
        val artist: String,
        val album: String,
        val durationMillis: Long,
        val positionMillis: Long,
        val albumArt: Bitmap?,
        val sourcePackageName: String,
        val sourceAppName: String,
        val isPlaying: Boolean
    ) : NowPlayingState
}
