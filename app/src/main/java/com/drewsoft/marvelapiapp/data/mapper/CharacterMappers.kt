package com.drewsoft.marvelapiapp.data.mapper

import com.drewsoft.marvelapiapp.data.remote.entity.CharacterDetailResponse
import com.drewsoft.marvelapiapp.data.remote.entity.CharactersListResponse
import com.drewsoft.marvelapiapp.domain.model.Link
import com.drewsoft.marvelapiapp.domain.model.characterlist.CharacterListItem
import com.drewsoft.marvelapiapp.domain.model.characterlist.CharactersListResult
import com.drewsoft.marvelapiapp.domain.model.Thumbnail
import com.drewsoft.marvelapiapp.domain.model.characterdetail.CharacterDetail
import java.lang.IllegalStateException

fun CharactersListResponse.toDomainModel(): CharactersListResult {
    val data = this.data
    val mappedCharacters = if (data?.results != null) {
        data.results.map {
            CharacterListItem(
                it?.id ?: -1, it?.name.orEmpty(), it?.description.orEmpty(), Thumbnail(
                    it?.thumbnail?.path.orEmpty(), it?.thumbnail?.extension.orEmpty()
                )
            )
        }
    } else {
        emptyList()
    }
    return CharactersListResult(data?.total ?: -1, data?.offset ?: -1, mappedCharacters)
}

fun CharacterDetailResponse.toDomainModel(): CharacterDetail {
    val data = this.data
    if (data?.results != null){
        if (data.results.size != 1){
            throw IllegalStateException("Character detail must be one result")
        }
        val resultItem = data.results.first()!!

        val comicList = resultItem.comics?.items?.map {
            it?.name ?: "N/A"
        } ?: emptyList()

        val linkList = resultItem.urls?.filterNotNull()?.map {
            Link(it.url?.replace("http://", "https://") ?: "", it.type ?: "other")
        } ?: emptyList()

        return CharacterDetail(
            resultItem.id ?: -1, resultItem.name.orEmpty(), resultItem.description.orEmpty(),
            Thumbnail(resultItem.thumbnail?.path.orEmpty(), resultItem.thumbnail?.extension.orEmpty()),
            comicList, linkList
        )
    }else{
        throw NullPointerException("Character detail cannot be null")
    }
}

