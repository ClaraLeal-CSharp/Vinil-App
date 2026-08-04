package br.com.vinilapp.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import br.com.vinilapp.app.navigation.VinilNavHost

/** Ponto de entrada da árvore Compose do aplicativo. */
@Composable
fun VinilApp() {
    val navController = rememberNavController()
    VinilNavHost(navController = navController)
}
