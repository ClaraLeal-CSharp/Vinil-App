package br.com.vinilapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.vinilapp.data.model.Album

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun VinilApp() {
    val albums = listOf(
        Album("The Dark Side of the Moon", "Pink Floyd", 1973),
        Album("Clube da Esquina", "Milton Nascimento & Lô Borges", 1972),
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("Minha coleção") }) },
    ) { innerPadding ->
        AlbumList(albums = albums, contentPadding = innerPadding)
    }
}

@Composable
private fun AlbumList(albums: List<Album>, contentPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = contentPadding.calculateTopPadding() + 16.dp,
            end = 16.dp,
            bottom = contentPadding.calculateBottomPadding() + 16.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(albums, key = { it.title }) { album ->
            AlbumCard(album)
        }
    }
}

@Composable
private fun AlbumCard(album: Album) {
    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = album.title, style = MaterialTheme.typography.titleMedium)
            Text(text = album.artist, style = MaterialTheme.typography.bodyMedium)
            Text(text = album.year.toString(), style = MaterialTheme.typography.labelMedium)
        }
    }
}
