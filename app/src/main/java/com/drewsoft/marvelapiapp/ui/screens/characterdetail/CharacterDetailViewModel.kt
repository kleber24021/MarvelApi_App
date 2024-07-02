package com.drewsoft.marvelapiapp.ui.screens.characterdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drewsoft.marvelapiapp.domain.usecases.GetCharacterDetailUseCase
import com.drewsoft.marvelapiapp.domain.model.characterdetail.CharacterDetail
import com.drewsoft.marvelapiapp.ui.screens.characterdetail.state.CharacterDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<CharacterDetailUiState> =
        MutableStateFlow(CharacterDetailUiState.Loading)
    val uiState: StateFlow<CharacterDetailUiState> = _uiState.asStateFlow()

    fun getCharacterDetail(charId: Int){
        viewModelScope.launch {
            getCharacterDetailUseCase(charId)
                .map<CharacterDetail, CharacterDetailUiState>{
                    CharacterDetailUiState.Success(it)
                }.catch {
                    emit(CharacterDetailUiState.Error(it))
                }.collect{
                    _uiState.value = it
                }
        }
    }
}