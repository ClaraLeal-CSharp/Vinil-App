package br.com.vinilapp.di

import android.content.Context
import android.media.session.MediaSessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMediaSessionManager(@ApplicationContext context: Context): MediaSessionManager =
        context.getSystemService(MediaSessionManager::class.java)
}
