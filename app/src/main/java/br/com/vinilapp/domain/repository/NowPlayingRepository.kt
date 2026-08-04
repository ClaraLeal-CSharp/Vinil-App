package br.com.vinilapp.domain.repository

import br.com.vinilapp.domain.model.NowPlayingState
import kotlinx.coroutines.flow.Flow

/** Contrato para as fontes que expõem o conteúdo reproduzido por outros apps. */
interface NowPlayingRepository {
    fun observeNowPlaying(): Flow<NowPlayingState>
}
