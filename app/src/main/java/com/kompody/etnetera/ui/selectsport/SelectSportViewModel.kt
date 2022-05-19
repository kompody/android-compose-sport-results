package com.kompody.etnetera.ui.selectsport

import androidx.lifecycle.viewModelScope
import com.kompody.etnetera.domain.entity.SportModel
import com.kompody.etnetera.domain.manager.SelectedSportManager
import com.kompody.etnetera.domain.usecase.GetSportsListUseCase
import com.kompody.etnetera.ui.base.BaseViewModel
import com.kompody.etnetera.ui.base.commandOf
import com.kompody.etnetera.ui.base.stateOf
import com.kompody.etnetera.ui.selectsport.model.SportItem
import com.kompody.etnetera.utils.ResourceDelegate
import com.kompody.etnetera.utils.extensions.io
import com.kompody.etnetera.utils.extensions.main
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectSportViewModel @Inject constructor(
    private val resourceDelegate: ResourceDelegate,
    private val getSportsListUseCase: GetSportsListUseCase,
    private val selectedSportManager: SelectedSportManager
) : BaseViewModel() {

    sealed class State {
        object Placeholder : State()
        object Error : State()
        class Success(
            val items: List<SportItem> = listOf()
        ) : State()
    }

    sealed class Action {
        object Refresh : Action()
        object BackPressed : Action()
        class SelectSport(val item: SportItem) : Action()
    }

    sealed class Command {
        object CloseScreen : Command()
    }

    val commands = commandOf<Command>()
    val state = stateOf<State>(State.Placeholder)
    val loading = stateOf(false)

    private var loadSportListJob: Job? = null

    init {
        state.update { State.Placeholder }
        loadSportList()
    }

    private fun loadSportList() {
        loadSportListJob?.cancel()
        loadSportListJob = viewModelScope.launch {
            loading.update { true }

            kotlin.runCatching { io { getSportsListUseCase.execute() } }
                .map { items ->
                    items.map { entity ->
                        SportItem(
                            id = entity.id,
                            name = entity.name
                        )
                    }
                }
                .onSuccess { items ->
                    main {
                        state.update { State.Success(items) }
                        loading.update { false }
                    }
                }
                .onFailure { throwable ->
                    main {
                        errors.execute(resourceDelegate, throwable)
                        state.update { State.Error }
                        loading.update { false }
                    }
                }
        }
    }

    private fun refresh() {
        loadSportList()
    }

    fun accept(action: Action) = when (action) {
        is Action.Refresh -> refresh()
        is Action.BackPressed -> commands.execute(Command.CloseScreen)
        is Action.SelectSport -> {
            selectedSportManager.setSelectedSport(
                SportModel(
                    id = action.item.id,
                    name = action.item.name
                )
            )
            commands.execute(Command.CloseScreen)
        }
    }
}