package com.drewsoft.marvelapiapp.ui.screens.characterdetail.state

import com.drewsoft.marvelapiapp.domain.model.characterdetail.CharacterDetail

sealed interface CharacterDetailUiState {
    data object Loading: CharacterDetailUiState
    data class Success(val characterDetail: CharacterDetail): CharacterDetailUiState
    data class Error(val throwable: Throwable): CharacterDetailUiState
}