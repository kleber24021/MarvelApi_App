package com.drewsoft.marvelapiapp.domain.usecases

import com.drewsoft.marvelapiapp.data.OrderBy
import com.drewsoft.marvelapiapp.data.repository.CharactersRepository
import javax.inject.Inject

class GetFilteredCharactersUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {
    suspend operator fun invoke(queryName: String, offSet: Int, orderBy: OrderBy){
        charactersRepository.getCharacters(
            queryName, offSet, orderBy
        )
    }
}