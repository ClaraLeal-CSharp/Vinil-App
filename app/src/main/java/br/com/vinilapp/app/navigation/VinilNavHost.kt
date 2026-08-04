package br.com.vinilapp.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.vinilapp.feature.nowplaying.presentation.NowPlayingRoute

/** Grafo central de navegação; novos recursos devem declarar seus destinos aqui. */
@Composable
fun VinilNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = AppDestination.NowPlaying.route,
        modifier = modifier
    ) {
        composable(route = AppDestination.NowPlaying.route) {
            NowPlayingRoute()
        }
    }
}
