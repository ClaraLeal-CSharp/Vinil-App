package br.com.vinilapp.domain.usecase

import br.com.vinilapp.domain.repository.NowPlayingRepository
import javax.inject.Inject

/** Caso de uso preparado para quando o repositório possuir uma fonte concreta. */
class ObserveNowPlayingUseCase @Inject constructor(
    private val repository: NowPlayingRepository
) {
    operator fun invoke() = repository.observeNowPlaying()
}
