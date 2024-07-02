package com.drewsoft.marvelapiapp.domain.model.characterlist

data class CharactersListResult(
    val totalResults: Int, val currentOffset: Int, val characters: List<CharacterListItem>
)
