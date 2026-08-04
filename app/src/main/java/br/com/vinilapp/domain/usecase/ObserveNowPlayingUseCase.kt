package br.com.vinilapp.domain.usecase

import br.com.vinilapp.domain.repository.NowPlayingRepository

/** Caso de uso preparado para quando o repositório possuir uma fonte concreta. */
class ObserveNowPlayingUseCase(
    private val repository: NowPlayingRepository
) {
    operator fun invoke() = repository.observeNowPlaying()
}
