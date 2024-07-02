package com.drewsoft.marvelapiapp.domain.model.characterlist

import com.drewsoft.marvelapiapp.domain.model.ImageFormat
import com.drewsoft.marvelapiapp.domain.model.Thumbnail

data class CharacterListItem(
    val id: Int, val name: String, val description: String, private val thumbnail: Thumbnail
){
    fun getImageUrl(imageFormat: ImageFormat) = thumbnail.getImageUrl(imageFormat)
}