package com.drewsoft.marvelapiapp.data.remote.api

import com.drewsoft.marvelapiapp.data.DataConsts
import com.drewsoft.marvelapiapp.data.DataConsts.CHARACTER_ID_QUERY
import com.drewsoft.marvelapiapp.data.DataConsts.NAME_STARTS_WITH_QUERY
import com.drewsoft.marvelapiapp.data.DataConsts.OFFSET_QUERY
import com.drewsoft.marvelapiapp.data.DataConsts.ORDER_BY_QUERY
import com.drewsoft.marvelapiapp.data.remote.model.CharacterDetailResponse
import com.drewsoft.marvelapiapp.data.remote.model.CharactersListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersClient {
    @GET(DataConsts.CHARACTERS_URL)
    suspend fun getCharactersList(
        @Query(NAME_STARTS_WITH_QUERY)
        queryName: String?,
        @Query(ORDER_BY_QUERY)
        orderBy: String?,
        @Query(OFFSET_QUERY)
        offset: Int?
    ): Response<CharactersListResponse>

    @GET(DataConsts.CHARACTER_DETAIL_URL)
    suspend fun getCharacterDetail(
        @Query(CHARACTER_ID_QUERY)
        characterId: Int
    ): Response<CharacterDetailResponse>
}