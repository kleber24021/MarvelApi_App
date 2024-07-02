package com.drewsoft.marvelapiapp.domain.model

data class CharactersResult(
    val totalResults: Int, val currentOffset: Int, val characters: List<Character>
)
