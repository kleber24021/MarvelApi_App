package com.drewsoft.marvelapiapp.domain.usecases

import com.drewsoft.marvelapiapp.data.DataConsts
import com.drewsoft.marvelapiapp.data.mapper.toDomainModel
import com.drewsoft.marvelapiapp.data.repository.CharactersRepository
import com.drewsoft.marvelapiapp.domain.model.characterlist.CharactersListResult
import com.drewsoft.marvelapiapp.domain.model.characterlist.OrderBy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {
    operator fun invoke(queryName: String?, offSet: Int, orderBy: OrderBy?): Flow<CharactersListResult> =
        charactersRepository.getCharacters(
            queryName, offSet, getQueryStringFromOrderBy(orderBy)
        ).filterNotNull().map {
            it.toDomainModel()
        }
    private fun getQueryStringFromOrderBy(orderBy: OrderBy?) : String? = when(orderBy){
        OrderBy.NAME -> DataConsts.NAME_QUERY
        OrderBy.MODIFIED -> DataConsts.MODIFIED_QUERY
        OrderBy.DESC_NAME -> DataConsts.DESC_NAME_QUERY
        OrderBy.DESC_MODIFIED -> DataConsts.DESC_MODIDIFIED_QUERY
        null -> null
    }
}