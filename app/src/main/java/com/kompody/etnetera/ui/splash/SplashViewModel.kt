package com.kompody.etnetera.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kompody.etnetera.ui.base.CommandOf
import com.kompody.etnetera.utils.extensions.timer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class SplashViewModel @Inject constructor(
) : ViewModel() {
    companion object {
        private const val DELAY = 5000L
    }

    sealed class Command {
        object OpenMainScreen : Command()
    }

    val commands = CommandOf<Command>()

    init {
        viewModelScope.launch {
            timer(Command.OpenMainScreen, DELAY)
                .collectLatest { command ->
                    commands.execute(command)
                }
        }
    }
}