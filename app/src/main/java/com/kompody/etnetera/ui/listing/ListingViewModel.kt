package com.kompody.etnetera.ui.listing

import androidx.lifecycle.viewModelScope
import com.kompody.etnetera.domain.usecase.FetchResultsUseCase
import com.kompody.etnetera.ui.base.BaseViewModel
import com.kompody.etnetera.ui.base.commandOf
import com.kompody.etnetera.ui.base.menu.MenuIds
import com.kompody.etnetera.ui.base.menu.PopupMenuAdapterItem
import com.kompody.etnetera.ui.base.stateOf
import com.kompody.etnetera.ui.listing.model.ResultItem
import com.kompody.etnetera.ui.listing.model.ResultItemFilter
import com.kompody.etnetera.utils.ResourceDelegate
import com.kompody.etnetera.utils.extensions.io
import com.kompody.etnetera.utils.extensions.main
import com.kompody.etnetera.utils.extensions.timer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ListingViewModel @Inject constructor(
    private val resourceDelegate: ResourceDelegate,
    private val fetchResultsUseCase: FetchResultsUseCase
) : BaseViewModel() {

    companion object {
        private val popupMenuItems = listOf(
            PopupMenuAdapterItem(
                id = MenuIds.FILTER_ALL,
                title = "All"
            ),
            PopupMenuAdapterItem(
                id = MenuIds.FILTER_ONLY_REMOTE,
                title = "Only remote"
            ),
            PopupMenuAdapterItem(
                id = MenuIds.FILTER_ONLY_LOCALE,
                title = "Only locale"
            )
        )
    }

    sealed class State {
        object Placeholder : State()
        object Error : State()
        object Empty : State()
        class Success(
            val items: List<ResultItem> = listOf(),
            val filters: List<PopupMenuAdapterItem> = popupMenuItems
        ) : State()
    }

    sealed class Action {
        object Refresh : Action()
        object AddButtonClick : Action()
        class ListingItemClick(val item: ResultItem) : Action()
        object FilterAllClick : Action()
        object FilterOnlyRemote : Action()
        object FilterOnlyLocale : Action()
    }

    sealed class Command {
        object OpenAddResultScreen : Command()
        class OpenListingItem(val item: ResultItem) : Command()
    }

    private val selectedFilter = stateOf(ResultItemFilter.All)

    val commands = commandOf<Command>()
    val state = stateOf<State>(State.Placeholder)
    val loading = stateOf(false)

    init {
        handleUpdateLocaleDatabase()
        state.update { State.Placeholder }
        refresh()
    }

    private fun handleUpdateLocaleDatabase() {
        viewModelScope.launch {
            fetchResultsUseCase.flowLocaleResults()
                .collectLatest {
                    refresh()
                }
        }
    }

    fun accept(action: Action) = when (action) {
        is Action.Refresh -> refresh()
        is Action.AddButtonClick -> commands.execute(Command.OpenAddResultScreen)
        is Action.ListingItemClick -> commands.execute(Command.OpenListingItem(action.item))
        is Action.FilterAllClick -> loadAllResults()
        is Action.FilterOnlyRemote -> loadOnlyRemoteResults()
        is Action.FilterOnlyLocale -> loadOnlyLocaleResults()
    }

    private fun refresh() {
        loading.update { true }
        if (Date().time.toInt() % 4 == 0) {
            viewModelScope.launch {
                timer(State.Error, 500)
                    .collectLatest {
                        showError()
                    }
            }
        } else {
            fetchResults()
        }
    }

    private fun loadAllResults() {
        selectedFilter.update { ResultItemFilter.All }
        fetchResults()
    }

    private fun loadOnlyRemoteResults() {
        selectedFilter.update { ResultItemFilter.OnlyRemote }
        fetchResults()
    }

    private fun loadOnlyLocaleResults() {
        selectedFilter.update { ResultItemFilter.OnlyLocale }
        fetchResults()
    }

    private fun fetchResults() {
        loading.update { true }
        viewModelScope.launch {
            kotlin.runCatching { io { fetchResultsUseCase.execute(selectedFilter.value) } }
                .onSuccess { items ->
                    main { showRecords(items) }
                }
                .onFailure { throwable ->
                    main {
                        errors.execute(resourceDelegate, throwable)
                        showError()
                    }
                }
        }
    }

    private fun showError() {
        state.update { State.Error }
        loading.update { false }
    }

    private fun showRecords(items: List<ResultItem>) {
        val newState = if (items.isEmpty()) {
            State.Empty
        } else {
            State.Success(
                items = items
            )
        }

        state.update { newState }
        loading.update { false }
    }
}