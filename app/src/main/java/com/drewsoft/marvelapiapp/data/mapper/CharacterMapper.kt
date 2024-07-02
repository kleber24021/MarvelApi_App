package com.drewsoft.marvelapiapp.data.mapper

import com.drewsoft.marvelapiapp.data.remote.model.CharactersListResponse
import com.drewsoft.marvelapiapp.domain.model.Character
import com.drewsoft.marvelapiapp.domain.model.CharactersResult
import com.drewsoft.marvelapiapp.domain.model.Thumbnail

fun CharactersListResponse.toDomainModel(): CharactersResult {
    val data = this.data
    val mappedCharacters = if (data?.results != null) {
        data.results.map {
            Character(
                it?.id ?: -1, it?.name.orEmpty(), it?.description.orEmpty(), Thumbnail(
                    it?.thumbnail?.path.orEmpty(), it?.thumbnail?.extension.orEmpty()
                )
            )
        }
    } else {
        emptyList()
    }
    return CharactersResult(data?.total ?: -1, data?.offset ?: -1, mappedCharacters)
}