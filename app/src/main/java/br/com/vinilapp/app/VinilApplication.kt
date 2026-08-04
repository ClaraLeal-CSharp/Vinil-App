package br.com.vinilapp.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/** Ponto de inicialização do grafo de injeção de dependências. */
@HiltAndroidApp
class VinilApplication : Application()
