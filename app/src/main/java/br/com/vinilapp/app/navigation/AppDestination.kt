package br.com.vinilapp.app.navigation

/** Destinos disponíveis na navegação principal do aplicativo. */
sealed class AppDestination(val route: String) {
    data object NowPlaying : AppDestination(route = "now_playing")
}
