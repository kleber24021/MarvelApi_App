package com.drewsoft.marvelapiapp.domain.usecases

import com.drewsoft.marvelapiapp.data.repository.CharactersRepository
import com.drewsoft.marvelapiapp.domain.model.CharactersResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {
    operator fun invoke(): Flow<CharactersResult> = charactersRepository.getCharacters(
        null, null, null
    ).filterNotNull()
}