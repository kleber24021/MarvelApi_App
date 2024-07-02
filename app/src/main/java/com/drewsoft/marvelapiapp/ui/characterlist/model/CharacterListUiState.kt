package com.drewsoft.marvelapiapp.ui.characterlist.model

import com.drewsoft.marvelapiapp.domain.model.CharactersResult

sealed interface CharacterListUiState {
    object Loading: CharacterListUiState
    data class Error(val throwable: Throwable): CharacterListUiState
    data class Success(val result: CharactersResult): CharacterListUiState
}