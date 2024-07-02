package com.drewsoft.marvelapiapp.data

import com.drewsoft.marvelapiapp.data.DataConsts.DESC_MODIDIFIED_QUERY
import com.drewsoft.marvelapiapp.data.DataConsts.DESC_NAME_QUERY
import com.drewsoft.marvelapiapp.data.DataConsts.MODIFIED_QUERY
import com.drewsoft.marvelapiapp.data.DataConsts.NAME_QUERY

object DataConsts {
    const val BASE_URL = "https://gateway.marvel.com/"
    const val CHARACTERS_URL = "v1/public/characters"
    const val CHARACTER_DETAIL_URL = "v1/public/{characterId}"
    const val CHARACTER_COMICS_URL = "$CHARACTER_DETAIL_URL/comics"
    const val NAME_QUERY = "name"
    const val MODIFIED_QUERY = "modified"
    const val DESC_NAME_QUERY = "-name"
    const val DESC_MODIDIFIED_QUERY = "-modified"
    const val NAME_STARTS_WITH_QUERY = "nameStartsWith"
    const val ORDER_BY_QUERY = "orderBy"
    const val OFFSET_QUERY = "offset"
    const val CHARACTER_ID_QUERY = "characterId"
    const val API_KEY_QUERY = "apikey"
}

enum class OrderBy(
    val queryString: String
) {
    NAME(NAME_QUERY), MODIFIED(MODIFIED_QUERY), DESC_NAME(DESC_NAME_QUERY), DESC_MODIFIED(
        DESC_MODIDIFIED_QUERY
    )
}