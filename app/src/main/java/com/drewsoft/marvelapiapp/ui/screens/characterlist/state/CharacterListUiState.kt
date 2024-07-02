package com.drewsoft.marvelapiapp.ui.screens.characterlist.state

import com.drewsoft.marvelapiapp.domain.model.characterlist.CharactersListResult

sealed interface CharacterListUiState {
    data object Loading: CharacterListUiState
    data class Error(val throwable: Throwable): CharacterListUiState
    data class Success(val result: CharactersListResult): CharacterListUiState
}