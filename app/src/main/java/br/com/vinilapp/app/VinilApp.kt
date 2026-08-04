package br.com.vinilapp.app

import androidx.compose.runtime.Composable
import br.com.vinilapp.feature.nowplaying.presentation.NowPlayingRoute

/** Ponto de entrada da árvore Compose do aplicativo. */
@Composable
fun VinilApp() {
    NowPlayingRoute()
}
