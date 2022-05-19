package com.kompody.etnetera.ui.addresult

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kompody.etnetera.domain.entity.ResultModel
import com.kompody.etnetera.domain.manager.SelectedSportManager
import com.kompody.etnetera.domain.usecase.PutResultToLocaleUseCase
import com.kompody.etnetera.domain.usecase.PutResultToRemoteUseCase
import com.kompody.etnetera.ui.base.BaseViewModel
import com.kompody.etnetera.ui.base.commandOf
import com.kompody.etnetera.ui.base.stateOf
import com.kompody.etnetera.utils.Const
import com.kompody.etnetera.utils.extensions.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddResultViewModel @Inject constructor(
    private val selectedSportManager: SelectedSportManager,
    private val putResultToRemoteUseCase: PutResultToRemoteUseCase,
    private val putResultToLocaleUseCase: PutResultToLocaleUseCase
) : BaseViewModel() {

    companion object {
        private const val DURATION_EMPTY = "0000000"
        private const val DURATION_REGEX =
            "^([01]\\d|2[0-3])([0-5]\\d)?(\\d?){3}"
    }

    data class State(
        val sportId: Int = Const.NO_ID,
        val sportName: String = String.empty,
        val place: String = String.empty,
        val duration: String = DURATION_EMPTY,
        val durationCursorPosition: Int = 0,
        val isRemote: Boolean = false,

        val isSportEmpty: Boolean = false,
        val isPlaceEmpty: Boolean = false,
        val isDurationEmpty: Boolean = false,
    )

    sealed class Action {
        object BackPressed : Action()
        object SelectSportClick : Action()
        class PlaceChange(val place: String) : Action()
        class DurationChange(val duration: String, val position: Int) : Action()
        object CleanDuration : Action()
        object FlagIsRemoteChange : Action()
        class FlagIsRemoteSetup(val flag: Boolean) : Action()
        object AddPressed : Action()
    }

    sealed class Command {
        object CloseScreen : Command()
        object OpenSelectSportScreen : Command()
        object ShowCompleteAddResultToRemoteMessage : Command()
        object ShowFailAddResultToRemoteMessage : Command()
        object ShowCompleteAddResultToLocaleMessage : Command()
        object ShowFailAddResultToLocaleMessage : Command()
    }

    val commands = commandOf<Command>()
    val state = stateOf(State())

    init {
        selectedSportManager.create(SavedStateHandle())

        viewModelScope.launch {
            selectedSportManager.observeSelectedSport { sportItem ->
                state.update { state ->
                    state.copy(
                        sportId = sportItem.id,
                        sportName = sportItem.name,
                        isSportEmpty = false
                    )
                }
            }
        }
    }

    fun accept(action: Action) = when (action) {
        is Action.BackPressed -> commands.execute(Command.CloseScreen)
        is Action.SelectSportClick -> commands.execute(Command.OpenSelectSportScreen)
        is Action.PlaceChange -> checkPlace(action.place)
        is Action.DurationChange -> checkDuration(action.duration, action.position)
        is Action.CleanDuration -> state.update {
            it.copy(
                duration = DURATION_EMPTY,
                durationCursorPosition = 0
            )
        }
        is Action.FlagIsRemoteChange -> state.update { it.copy(isRemote = !it.isRemote) }
        is Action.FlagIsRemoteSetup -> state.update { it.copy(isRemote = action.flag) }
        is Action.AddPressed -> addResult()
    }

    private fun addResult() {
        if (state.value.sportId == Const.NO_ID) {
            state.update { it.copy(isSportEmpty = true) }
            return
        } else {
            state.update { it.copy(isSportEmpty = false) }
        }

        if (state.value.place.isEmpty()) {
            state.update { it.copy(isPlaceEmpty = true) }
            return
        } else {
            state.update { it.copy(isPlaceEmpty = false) }
        }

        if (state.value.duration == DURATION_EMPTY) {
            state.update { it.copy(isDurationEmpty = true) }
            return
        } else {
            state.update { it.copy(isDurationEmpty = false) }
        }

        if (state.value.isRemote) {
            addResultToRemote()
        } else {
            addResultToLocale()
        }
    }

    private fun addResultToRemote() {
        viewModelScope.launch {
            kotlin.runCatching { putResultToRemote() }
                .onSuccess { handleCompletePutToRemote() }
                .onFailure { handleFailPutToRemote() }
        }
    }

    private suspend fun putResultToRemote() = io {
        putResultToRemoteUseCase.execute(
            model = ResultModel(
                sportId = state.value.sportId,
                sportName = state.value.sportName,
                place = state.value.place.toInt(),
                duration = state.value.duration.toInt(),
                date = calendarOf(Date()).time.time,
                type = ResultModel.Type.Locale,
            )
        )
    }

    private suspend fun handleCompletePutToRemote() {
        main {
            commands.execute(
                Command.ShowCompleteAddResultToRemoteMessage,
                Command.CloseScreen
            )
        }
    }

    private suspend fun handleFailPutToRemote() {
        main { commands.execute(Command.ShowFailAddResultToRemoteMessage) }
    }

    private fun addResultToLocale() {
        viewModelScope.launch {
            kotlin.runCatching { putResultToLocale() }
                .onSuccess { handleCompletePutToLocale() }
                .onFailure { handleFailPutToLocale() }
        }
    }

    private suspend fun putResultToLocale() = io {
        putResultToLocaleUseCase.execute(
            model = ResultModel(
                sportId = state.value.sportId,
                sportName = state.value.sportName,
                place = state.value.place.toInt(),
                duration = state.value.duration.toInt(),
                date = calendarOf(Date()).time.time,
                type = ResultModel.Type.Locale,
            )
        )
    }

    private suspend fun handleCompletePutToLocale() {
        main {
            commands.execute(
                Command.ShowCompleteAddResultToLocaleMessage,
                Command.CloseScreen
            )
        }
    }

    private suspend fun handleFailPutToLocale() {
        main { commands.execute(Command.ShowFailAddResultToLocaleMessage) }
    }

    private fun checkPlace(place: String) {
        val value = place.filter { it.isDigit() }
        val newPlace = if (value.length > Const.MAX_PLACE_LENGTH) {
            value.substring(0, Const.MAX_PLACE_LENGTH)
        } else {
            value
        }

        state.update {
            it.copy(
                place = newPlace,
                isPlaceEmpty = false
            )
        }
    }

    private fun checkDuration(duration: String, position: Int) {
        val number = duration.filter { it.isDigit() }

        val sb = StringBuilder()

        val durationStr = if (number.length > Const.MAX_DURATION_LENGTH) {
            number.substring(0, Const.MAX_DURATION_LENGTH)
        } else {
            number
        }

        sb.append(durationStr)
        repeat((0 until Const.MAX_DURATION_LENGTH - sb.length).count()) {
            sb.append(String.zero)
        }

        val newDuration = sb.toString()

        val regexItem = DURATION_REGEX.toRegex()
            .matchEntire(newDuration)?.groupValues?.first()

        val stateDuration = if (regexItem != null) {
            newDuration
        } else {
            state.value.duration
        }

        state.update {
            it.copy(
                duration = stateDuration,
                durationCursorPosition = position,
                isDurationEmpty = false
            )
        }
    }
}