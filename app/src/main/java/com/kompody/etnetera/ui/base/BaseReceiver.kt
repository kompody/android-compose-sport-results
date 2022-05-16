package com.kompody.etnetera.ui.base

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

open class BaseReceiver<T> {
    private val _events = MutableSharedFlow<T>()
    val events = _events.asSharedFlow()

    suspend fun postEvent(event: T) {
        _events.emit(event)
    }
}