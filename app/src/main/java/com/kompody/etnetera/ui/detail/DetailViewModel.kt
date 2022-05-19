package com.kompody.etnetera.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.google.gson.Gson
import com.kompody.etnetera.ui.base.BaseViewModel
import com.kompody.etnetera.ui.base.commandOf
import com.kompody.etnetera.ui.base.stateOf
import com.kompody.etnetera.ui.listing.model.ResultItem
import com.kompody.etnetera.utils.extensions.fromJson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val gson: Gson
) : BaseViewModel() {

    data class State(
        val resultItem: ResultItem? = null
    )

    sealed class Action {
        object BackPressed : Action()
    }

    sealed class Command {
        object CloseScreen : Command()
    }

    val state = stateOf(State())
    val commands = commandOf<Command>()

    init {
        state.update { it.copy(resultItem = gson.fromJson(savedStateHandle.get("item")!!)) }
    }

    fun accept(action: Action) = when (action) {
        is Action.BackPressed -> commands.execute(Command.CloseScreen)
    }
}