package br.com.vinilapp.feature.nowplaying.presentation

/** Estado de apresentação mockado; ainda não há integração com sessões de mídia. */
data class NowPlayingUiState(
    val title: String = "",
    val artist: String = "",
    val source: String = "",
    val elapsedTime: String = "",
    val duration: String = "",
    val progress: Float = 0.42f,
    val isPlaying: Boolean = true
)
