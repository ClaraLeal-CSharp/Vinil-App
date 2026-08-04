package br.com.vinilapp.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** Ponto de extensão para vincular contratos de domínio às implementações de dados. */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule
