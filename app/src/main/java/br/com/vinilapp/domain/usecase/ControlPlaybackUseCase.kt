package br.com.vinilapp.domain.usecase

import br.com.vinilapp.domain.model.PlaybackCommand
import br.com.vinilapp.domain.repository.NowPlayingRepository
import javax.inject.Inject

/** Encaminha comandos de transporte para a sessão de mídia ativa. */
class ControlPlaybackUseCase @Inject constructor(
    private val repository: NowPlayingRepository
) {
    operator fun invoke(command: PlaybackCommand) {
        repository.sendPlaybackCommand(command)
    }
}
