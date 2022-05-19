package com.kompody.etnetera.ui.selectsport

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.kompody.etnetera.R
import com.kompody.etnetera.ui.selectsport.compose.SportItemItems
import com.kompody.etnetera.ui.selectsport.model.SportItem
import com.kompody.etnetera.ui.theme.MainTheme
import com.kompody.etnetera.ui.view.LoadErrorContent
import com.kompody.etnetera.ui.view.LoadingContent
import com.kompody.etnetera.ui.view.PlaceholderContent
import kotlinx.coroutines.flow.collect

@Composable
fun SelectSportScreen(
    onBackPressed: () -> Unit = {}
) {
    val viewModel: SelectSportViewModel = hiltViewModel()

    LaunchedEffect(true) {
        viewModel.commands.flow.collect { command ->
            when (command) {
                is SelectSportViewModel.Command.CloseScreen -> onBackPressed()
            }
        }
    }

    MainTheme {
        SelectSportContent(
            onRefresh = { viewModel.accept(SelectSportViewModel.Action.Refresh) },
            onSportItemClick = { viewModel.accept(SelectSportViewModel.Action.SelectSport(it)) },
            viewModel = viewModel
        )
    }
}

@Composable
private fun SelectSportContent(
    onRefresh: () -> Unit = {},
    onSportItemClick: (SportItem) -> Unit = {},
    viewModel: SelectSportViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.select_sport_title)) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.accept(SelectSportViewModel.Action.BackPressed) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val state by viewModel.state.flow.collectAsState()
        val loading by viewModel.loading.flow.collectAsState()

        val insetsModifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)

        SelectSportContent(
            onRefresh = onRefresh,
            onSportItemClick = onSportItemClick,
            loading = loading,
            state = state,
            modifier = insetsModifier
        )
    }
}

@Composable
private fun SelectSportContent(
    onRefresh: () -> Unit = {},
    onSportItemClick: (SportItem) -> Unit = {},
    loading: Boolean,
    state: SelectSportViewModel.State,
    modifier: Modifier
) {
    LoadingContent(
        loading = loading,
        onRefresh = onRefresh,
        modifier = modifier
    ) {
        when (state) {
            is SelectSportViewModel.State.Success -> SportItemItems(
                onSportItemClick = onSportItemClick,
                items = state.items
            )
            is SelectSportViewModel.State.Error -> LoadErrorContent(modifier)
            else -> PlaceholderContent(modifier)
        }
    }
}