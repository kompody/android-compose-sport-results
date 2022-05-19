package com.kompody.etnetera.ui.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StateOf<T>(initValue: T) {
    private val _state = MutableStateFlow(initValue)
    val flow: StateFlow<T>
        get() = _state.asStateFlow()

    val value get() = _state.value

    fun update(map: (T) -> T) {
        _state.update { map(_state.value) }
    }
}

fun <T> stateOf(initValue: T) = StateOf(initValue)