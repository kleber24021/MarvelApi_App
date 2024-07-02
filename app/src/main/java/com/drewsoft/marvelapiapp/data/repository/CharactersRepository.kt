package com.drewsoft.marvelapiapp.data.repository

import com.drewsoft.marvelapiapp.data.remote.api.CharactersClient
import com.drewsoft.marvelapiapp.data.remote.entity.CharacterDetailResponse
import com.drewsoft.marvelapiapp.data.remote.entity.CharactersListResponse
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
        orderBy: String?
    ): Flow<CharactersListResponse?>{
        return flow {
            val result = charactersClient.getCharactersList(
                queryName, orderBy, offset
            ).body()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getCharacterDetail(
        characterId: Int
    ): Flow<CharacterDetailResponse?>{
        return flow {
            val result = charactersClient.getCharacterDetail(
                characterId
            ).body()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}