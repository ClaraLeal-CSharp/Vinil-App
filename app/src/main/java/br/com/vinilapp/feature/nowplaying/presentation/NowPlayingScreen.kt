package br.com.vinilapp.feature.nowplaying.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.vinilapp.R
import br.com.vinilapp.core.designsystem.AppDimensions

/** Tela base sem acesso a APIs de mídia nesta etapa. */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NowPlayingScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.now_playing_title)) }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = AppDimensions.ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(
                AppDimensions.ContentSpacing,
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.now_playing_unavailable),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = stringResource(R.string.now_playing_setup_description),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
