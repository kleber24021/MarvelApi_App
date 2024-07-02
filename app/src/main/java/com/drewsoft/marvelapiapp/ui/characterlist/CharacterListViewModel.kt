package com.drewsoft.marvelapiapp.ui.characterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drewsoft.marvelapiapp.domain.usecases.GetCharactersUseCase
import com.drewsoft.marvelapiapp.domain.usecases.GetFilteredCharactersUseCase
import com.drewsoft.marvelapiapp.ui.characterlist.CharacterListUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    getCharactersUseCase: GetCharactersUseCase,
    getFilteredCharactersUseCase: GetFilteredCharactersUseCase
) : ViewModel() {

    val uiState: StateFlow<CharacterListUiState> = getCharactersUseCase().map(::Success)
        .catch { CharacterListUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CharacterListUiState.Loading)

}