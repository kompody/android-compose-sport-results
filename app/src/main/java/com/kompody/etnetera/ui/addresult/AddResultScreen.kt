package com.kompody.etnetera.ui.addresult

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kompody.etnetera.R
import com.kompody.etnetera.ui.base.transformation.DateTransformation
import com.kompody.etnetera.ui.theme.MainTheme
import com.kompody.etnetera.utils.extensions.empty
import kotlinx.coroutines.flow.collect

@Composable
fun AddResultScreen(
    onBackPressed: () -> Unit = {},
    openSelectSportScreen: () -> Unit = {}
) {
    val viewModel: AddResultViewModel = hiltViewModel()
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val completeRemoteMessage =
        stringResource(id = R.string.add_result_complete_add_result_to_remote_message)
    val failRemoteMessage =
        stringResource(id = R.string.add_result_fail_add_result_to_remote_message)
    val completeLocaleMessage =
        stringResource(id = R.string.add_result_complete_add_result_to_locale_message)
    val failLocaleMessage =
        stringResource(id = R.string.add_result_fail_add_result_to_locale_message)

    LaunchedEffect(true) {
        viewModel.commands.flow.collect { command ->
            when (command) {
                is AddResultViewModel.Command.CloseScreen -> onBackPressed()
                is AddResultViewModel.Command.OpenSelectSportScreen -> openSelectSportScreen()
                is AddResultViewModel.Command.ShowCompleteAddResultToRemoteMessage -> {
                    scaffoldState.snackbarHostState.showSnackbar(completeRemoteMessage)
                }
                is AddResultViewModel.Command.ShowFailAddResultToRemoteMessage -> {
                    scaffoldState.snackbarHostState.showSnackbar(failRemoteMessage)
                }
                is AddResultViewModel.Command.ShowCompleteAddResultToLocaleMessage -> {
                    scaffoldState.snackbarHostState.showSnackbar(completeLocaleMessage)
                }
                is AddResultViewModel.Command.ShowFailAddResultToLocaleMessage -> {
                    scaffoldState.snackbarHostState.showSnackbar(failLocaleMessage)
                }
            }
        }
    }

    MainTheme {
        AddResultScreen(
            viewModel = viewModel,
            scaffoldState = scaffoldState
        )
    }
}

@Composable
private fun AddResultScreen(
    viewModel: AddResultViewModel,
    scaffoldState: ScaffoldState
) {
    AddResultContent(
        viewModel = viewModel,
        scaffoldState = scaffoldState
    )
}

@Composable
private fun AddResultContent(
    viewModel: AddResultViewModel,
    scaffoldState: ScaffoldState
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.add_result_title)) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.accept(AddResultViewModel.Action.BackPressed) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { viewModel.accept(AddResultViewModel.Action.AddPressed) },
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.add_result_add_button_title))
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val state by viewModel.state.flow.collectAsState()

        val insetsModifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)

        AddResultContent(
            onSelectSportClick = { viewModel.accept(AddResultViewModel.Action.SelectSportClick) },
            onPlaceChange = { viewModel.accept(AddResultViewModel.Action.PlaceChange(it)) },
            onDurationChange = { duration, positon ->
                viewModel.accept(
                    AddResultViewModel.Action.DurationChange(
                        duration,
                        positon
                    )
                )
            },
            onCleanDuration = { viewModel.accept(AddResultViewModel.Action.CleanDuration) },
            onFlagIsRemoteChange = { viewModel.accept(AddResultViewModel.Action.FlagIsRemoteChange) },
            onFlagIsRemoteSetup = { viewModel.accept(AddResultViewModel.Action.FlagIsRemoteSetup(it)) },
            state = state,
            modifier = insetsModifier
        )
    }
}

@Composable
private fun AddResultContent(
    onSelectSportClick: () -> Unit = {},
    onPlaceChange: (String) -> Unit = {},
    onDurationChange: (String, Int) -> Unit = { _, _ -> },
    onCleanDuration: () -> Unit = {},
    onFlagIsRemoteChange: () -> Unit = {},
    onFlagIsRemoteSetup: (Boolean) -> Unit = {},
    state: AddResultViewModel.State,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(top = 24.dp, bottom = 16.dp)
    ) {
        item { SportNameField(onSelectSportClick, state) }
        item { Spacer(modifier = Modifier.height(8.dp)) }
        item { PlaceField(state, onPlaceChange) }
        item { Spacer(modifier = Modifier.height(8.dp)) }
        item { DurationField(state, onDurationChange, onCleanDuration) }
        item { Spacer(modifier = Modifier.height(12.dp)) }
        item { FlagRemote(onFlagIsRemoteChange, state, onFlagIsRemoteSetup) }
    }
}

@Composable
private fun FlagRemote(
    onFlagIsRemoteChange: () -> Unit,
    state: AddResultViewModel.State,
    onFlagIsRemoteSetup: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onFlagIsRemoteChange() }
    ) {
        Text(
            text = stringResource(id = R.string.add_result_storage_title),
            modifier = Modifier
                .height(24.dp)
                .weight(1f)
                .padding(start = 32.dp)
        )

        Checkbox(
            checked = state.isRemote,
            onCheckedChange = { onFlagIsRemoteSetup(!state.isRemote) },
            modifier = Modifier
                .padding(end = 16.dp)
        )
    }
}

@Composable
private fun DurationField(
    state: AddResultViewModel.State,
    onDurationChange: (String, Int) -> Unit,
    onCleanDuration: () -> Unit
) {
    Column {
        OutlinedTextField(
            value = TextFieldValue(
                text = state.duration,
                selection = TextRange(state.durationCursorPosition)
            ),
            onValueChange = {
                onDurationChange(it.text, it.selection.start)
            },
            label = { Text(text = stringResource(id = R.string.add_result_duration_label)) },
            visualTransformation = DateTransformation(),
            placeholder = { Text(text = stringResource(id = R.string.add_result_duration_hint)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            trailingIcon = {
                if (state.duration.isEmpty()) return@OutlinedTextField

                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_clear_24),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            onCleanDuration()
                        }
                )
            },
            isError = state.isDurationEmpty,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        if (state.isDurationEmpty) {
            Text(
                text = stringResource(id = R.string.add_result_duration_error),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp, top = 0.dp)
            )
        }
    }
}

@Composable
private fun PlaceField(
    state: AddResultViewModel.State,
    onPlaceChange: (String) -> Unit
) {
    Column {
        OutlinedTextField(
            value = state.place,
            onValueChange = { onPlaceChange(it) },
            label = { Text(text = stringResource(id = R.string.add_result_place_label)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            trailingIcon = {
                if (state.place.isEmpty()) return@OutlinedTextField

                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_clear_24),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            onPlaceChange(String.empty)
                        }
                )
            },
            isError = state.isPlaceEmpty,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        if (state.isPlaceEmpty) {
            Text(
                text = stringResource(id = R.string.add_result_place_error),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp, top = 0.dp)
            )
        }
    }
}

@Composable
private fun SportNameField(
    onSelectSportClick: () -> Unit,
    state: AddResultViewModel.State
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { onSelectSportClick() }
        ) {
            OutlinedTextField(
                value = state.sportName,
                onValueChange = { },
                readOnly = true,
                enabled = state.isSportEmpty,
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                    backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0f),
                    disabledIndicatorColor = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.UnfocusedIndicatorLineOpacity),
                    disabledTrailingIconColor = MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
                    disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
                    disabledPlaceholderColor = MaterialTheme.colors.onSurface.copy(
                        ContentAlpha.medium
                    )
                ),
                placeholder = { Text(text = stringResource(id = R.string.add_result_name_hint)) },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_down_24),
                        contentDescription = null
                    )
                },
                isError = state.isSportEmpty,
                modifier = Modifier
                    .onFocusChanged {
                        if (it.isFocused) {
                            onSelectSportClick()
                        }
                    }
                    .fillMaxWidth()
            )
        }
        if (state.isSportEmpty) {
            Text(
                text = stringResource(id = R.string.add_result_name_error),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp, top = 0.dp)
            )
        }
    }
}