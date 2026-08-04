package br.com.vinilapp.domain.model

/** Comando de transporte enviado ao player que expõe a sessão de mídia ativa. */
enum class PlaybackCommand {
    Previous,
    PlayPause,
    Next
}
