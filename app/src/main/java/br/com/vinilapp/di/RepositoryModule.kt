package br.com.vinilapp.di

import br.com.vinilapp.data.mediaplayback.AndroidMediaSessionDataSource
import br.com.vinilapp.data.mediaplayback.MediaSessionDataSource
import br.com.vinilapp.data.repository.NowPlayingRepositoryImpl
import br.com.vinilapp.domain.repository.NowPlayingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindNowPlayingRepository(implementation: NowPlayingRepositoryImpl): NowPlayingRepository

    @Binds
    abstract fun bindMediaSessionDataSource(implementation: AndroidMediaSessionDataSource): MediaSessionDataSource
}
