package br.com.vinilapp.feature.nowplaying.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.vinilapp.R
import br.com.vinilapp.core.designsystem.theme.VinilTheme

/** Tela base sem acesso a APIs de mídia nesta etapa. */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NowPlayingScreen() {
    val colors = VinilTheme.colors
    val fonts = VinilTheme.fonts
    val sizes = VinilTheme.sizes
    val backgrounds = VinilTheme.backgrounds

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(backgrounds.app),
        containerColor = colors.transparent,
        contentColor = colors.onBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(start = sizes.appBarTitleInset),
                        text = stringResource(R.string.now_playing_title),
                        style = fonts.title,
                        color = colors.onSurface
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.surface,
                    titleContentColor = colors.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = sizes.screenPadding),
            verticalArrangement = Arrangement.spacedBy(
                sizes.contentSpacing,
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.now_playing_unavailable),
                style = fonts.title,
                color = colors.onBackground
            )
            Text(
                text = stringResource(R.string.now_playing_setup_description),
                style = fonts.body,
                color = colors.onSurfaceVariant
            )
        }
    }
}
