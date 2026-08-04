package br.com.vinilapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.vinilapp.ui.VinilApp
import br.com.vinilapp.ui.theme.VinilAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VinilAppTheme {
                VinilApp()
            }
        }
    }
}
