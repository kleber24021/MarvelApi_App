package com.drewsoft.marvelapiapp.ui.characterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drewsoft.marvelapiapp.domain.DomainConsts.PAGE_SIZE
import com.drewsoft.marvelapiapp.domain.model.CharactersResult
import com.drewsoft.marvelapiapp.domain.model.OrderBy
import com.drewsoft.marvelapiapp.domain.usecases.GetCharactersUseCase
import com.drewsoft.marvelapiapp.ui.characterlist.model.CharacterListUiState
import com.drewsoft.marvelapiapp.ui.characterlist.model.FilterCriteria
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<CharacterListUiState> =
        MutableStateFlow(CharacterListUiState.Loading)
    val uiState: StateFlow<CharacterListUiState> = _uiState.asStateFlow()

    private val _filterCriteria: MutableStateFlow<FilterCriteria> =
        MutableStateFlow(FilterCriteria())
    val filterCriteria: StateFlow<FilterCriteria> = _filterCriteria.asStateFlow()

    init {
        observeCharacters()
    }

    @OptIn(FlowPreview::class)
    private fun observeCharacters() {
        viewModelScope.launch {
            _filterCriteria
                .debounce(500)
                .flatMapLatest { criteria ->
                getCharactersUseCase(
                    criteria.name, criteria.offset, criteria.order
                )
            }.map<CharactersResult, CharacterListUiState> { result ->
                val currentState = _uiState.value
                if (_filterCriteria.value.offset == 0 || currentState !is CharacterListUiState.Success) {
                    CharacterListUiState.Success(result)
                } else {
                    val currentCharacters = currentState.result.characters
                    CharacterListUiState.Success(
                        result.copy(characters = currentCharacters + result.characters)
                    )
                }
            }.catch { exception ->
                emit(CharacterListUiState.Error(exception))
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun loadMoreCharacters() {
        val currentCriteria = _filterCriteria.value
        if (_uiState.value is CharacterListUiState.Success){
            if ((_uiState.value as CharacterListUiState.Success).result.totalResults - _filterCriteria.value.offset > PAGE_SIZE){
                _filterCriteria.value = currentCriteria.copy(
                    offset = currentCriteria.offset + PAGE_SIZE
                )
            }
        }else{
            _filterCriteria.value = FilterCriteria()
        }
    }

    fun setFilter(characterName: String, orderBy: OrderBy?) {
        val currentCriteria = _filterCriteria.value
        //Si los criterios de busqueda cambian, se "reinicia" el objeto, para empezar desde 0 en el offset
        if (currentCriteria.name != characterName || currentCriteria.order != orderBy) {
            _uiState.value = CharacterListUiState.Loading
            _filterCriteria.value = FilterCriteria(
                name = characterName.ifEmpty { null }, order = orderBy
            )
        }
    }
}