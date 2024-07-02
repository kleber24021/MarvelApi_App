package com.drewsoft.marvelapiapp.domain.model.characterdetail

import com.drewsoft.marvelapiapp.domain.model.Link
import com.drewsoft.marvelapiapp.domain.model.Thumbnail

data class CharacterDetail(
    val id: Int,
    val characterName: String,
    val description: String,
    val thumbnail: Thumbnail,
    val comicList: List<String>,
    val usefulLinks: List<Link>
)
