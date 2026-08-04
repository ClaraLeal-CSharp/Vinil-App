package br.com.vinilapp.data.mediaplayback

import br.com.vinilapp.domain.model.NowPlayingState
import br.com.vinilapp.domain.model.PlaybackCommand
import kotlinx.coroutines.flow.Flow

/** Fonte de sessões de mídia expostas pelo Android. */
interface MediaSessionDataSource {
    fun observeNowPlaying(): Flow<NowPlayingState>

    fun sendPlaybackCommand(command: PlaybackCommand)
}
