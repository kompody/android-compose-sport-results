@file:OptIn(ExperimentalCoroutinesApi::class)

package com.kompody.etnetera.ui.base

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

open class CommandOf<T> {
    private val _command = MutableSharedFlow<T?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val flow = _command.asSharedFlow()

    open fun execute(command: T? = null) {
        _command.tryEmit(command)
        _command.resetReplayCache()
    }

    fun execute(vararg commands: T) {
        commands.forEach {
            _command.tryEmit(it)
        }
        _command.resetReplayCache()
    }
}

fun <T> commandOf() = CommandOf<T>()