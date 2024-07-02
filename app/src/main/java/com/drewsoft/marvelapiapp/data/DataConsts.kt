package com.drewsoft.marvelapiapp.data

object DataConsts {
    const val BASE_URL = "https://gateway.marvel.com/"
    const val CHARACTERS_URL = "v1/public/characters"
    const val CHARACTER_ID_QUERY = "characterId"
    const val CHARACTER_DETAIL_URL = "$CHARACTERS_URL/{$CHARACTER_ID_QUERY}"
    const val NAME_QUERY = "name"
    const val MODIFIED_QUERY = "modified"
    const val DESC_NAME_QUERY = "-name"
    const val DESC_MODIDIFIED_QUERY = "-modified"
    const val NAME_STARTS_WITH_QUERY = "nameStartsWith"
    const val ORDER_BY_QUERY = "orderBy"
    const val OFFSET_QUERY = "offset"
    const val API_KEY_QUERY = "apikey"
}