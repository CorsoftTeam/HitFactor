package com.corsoft.common.mvvm

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface ScreenState

abstract class BaseViewModel<State : ScreenState>(initialState: State) :
    ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    fun updateState(newState: State) {
        _uiState.update { newState }
    }
}