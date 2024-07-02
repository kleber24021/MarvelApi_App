package com.drewsoft.marvelapiapp.domain.usecases

import com.drewsoft.marvelapiapp.data.mapper.toDomainModel
import com.drewsoft.marvelapiapp.data.repository.CharactersRepository
import com.drewsoft.marvelapiapp.domain.model.characterdetail.CharacterDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {
    operator fun invoke(characterId: Int): Flow<CharacterDetail> =
        charactersRepository.getCharacterDetail(characterId).filterNotNull()
            .map {
                it.toDomainModel()
            }
}