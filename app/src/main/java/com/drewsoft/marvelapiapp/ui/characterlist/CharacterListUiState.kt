package com.drewsoft.marvelapiapp.ui.characterlist

import com.drewsoft.marvelapiapp.domain.model.CharactersResult

sealed interface CharacterListUiState {
    object Loading:CharacterListUiState
    data class Error(val throwable: Throwable): CharacterListUiState
    data class Success(val characters: CharactersResult): CharacterListUiState
}