package br.com.vinilapp.data.repository

import br.com.vinilapp.data.mediaplayback.MediaSessionDataSource
import br.com.vinilapp.domain.model.NowPlayingState
import br.com.vinilapp.domain.model.PlaybackCommand
import br.com.vinilapp.domain.repository.NowPlayingRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class NowPlayingRepositoryImpl @Inject constructor(
    private val mediaSessionDataSource: MediaSessionDataSource
) : NowPlayingRepository {
    override fun observeNowPlaying(): Flow<NowPlayingState> = mediaSessionDataSource.observeNowPlaying()

    override fun sendPlaybackCommand(command: PlaybackCommand) {
        mediaSessionDataSource.sendPlaybackCommand(command)
    }
}
