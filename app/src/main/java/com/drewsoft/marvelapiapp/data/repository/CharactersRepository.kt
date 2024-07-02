package com.drewsoft.marvelapiapp.data.repository

import com.drewsoft.marvelapiapp.data.OrderBy
import com.drewsoft.marvelapiapp.data.mapper.toDomainModel
import com.drewsoft.marvelapiapp.data.remote.api.CharactersClient
import com.drewsoft.marvelapiapp.domain.model.CharactersResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val charactersClient: CharactersClient
){
    fun getCharacters(
        queryName: String?,
        offset: Int?,
        orderBy: OrderBy?
    ): Flow<CharactersResult?>{
        return flow {
            val result = charactersClient.getCharactersList(
                queryName, orderBy?.queryString, offset
            ).body()?.toDomainModel()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}