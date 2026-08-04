package br.com.vinilapp.domain.model

/** Estado de domínio que será preenchido pelas fontes oficiais de mídia do Android. */
sealed interface NowPlayingState {
    data object Unavailable : NowPlayingState
}
