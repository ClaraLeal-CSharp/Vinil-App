package br.com.vinilapp.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** Módulo reservado para dependências que pertencem a todo o processo. */
@Module
@InstallIn(SingletonComponent::class)
object AppModule
