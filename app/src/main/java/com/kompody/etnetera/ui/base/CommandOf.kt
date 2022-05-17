package com.kompody.etnetera.ui.base

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

open class CommandOf<T> {
    private val _command = MutableSharedFlow<T?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val flow = _command

    open fun execute(command: T? = null) {
        _command.tryEmit(command)
    }
}

fun <T> commandOf() = CommandOf<T>()