@file:OptIn(InternalCoroutinesApi::class)

package com.kompody.etnetera.ui.listing

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
import com.kompody.etnetera.ui.listing.view.listing.FilterMenu
import com.kompody.etnetera.ui.listing.view.listing.ResultListingItems
import com.kompody.etnetera.ui.theme.MainTheme
import com.kompody.etnetera.ui.view.EmptyContent
import com.kompody.etnetera.ui.view.LoadErrorContent
import com.kompody.etnetera.ui.view.LoadingContent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect

@Composable
fun ListingScreen() {
    val viewModel: ListingViewModel = hiltViewModel()

    LaunchedEffect(true) {
        viewModel.commands.flow.collect { command ->
            when (command) {
                is ListingViewModel.Command.ShowFilterDialog -> {}
                is ListingViewModel.Command.OpenAddResultScreen -> {}
                is ListingViewModel.Command.OpenListingItem -> {}
            }
        }
    }

    MainTheme {
        ListingScreen(
            onRefresh = { viewModel.accept(ListingViewModel.Action.Refresh) },
            onFilterAllClick = { viewModel.accept(ListingViewModel.Action.FilterAllClick) },
            onFilterOnlyRemoteClick = { viewModel.accept(ListingViewModel.Action.FilterOnlyRemote) },
            onFilterOnlyLocaleClick = { viewModel.accept(ListingViewModel.Action.FilterOnlyLocale) },
            onAddClick = { viewModel.accept(ListingViewModel.Action.AddButtonClick) },
            viewModel = viewModel
        )
    }
}

@Composable
private fun ListingScreen(
    onRefresh: () -> Unit = {},
    onFilterAllClick: () -> Unit,
    onFilterOnlyRemoteClick: () -> Unit,
    onFilterOnlyLocaleClick: () -> Unit,
    onAddClick: () -> Unit = {},
    viewModel: ListingViewModel
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    val actionLabel = stringResource(id = R.string.refresh)
    LaunchedEffect(scaffoldState.snackbarHostState) {
        viewModel.errors.flow.collect { error ->
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = error.toString(),
                actionLabel = actionLabel
            )
            when (result) {
                SnackbarResult.Dismissed -> {
                }
                SnackbarResult.ActionPerformed -> {
                    onRefresh.invoke()
                }
            }
        }
    }

    ListingContent(
        viewModel = viewModel,
        onRefresh = onRefresh,
        onFilterAllClick = onFilterAllClick,
        onFilterOnlyRemoteClick = onFilterOnlyRemoteClick,
        onFilterOnlyLocaleClick = onFilterOnlyLocaleClick,
        onAddClick = onAddClick,
        scaffoldState = scaffoldState
    )
}

@Composable
private fun ListingContent(
    viewModel: ListingViewModel,
    onRefresh: () -> Unit = {},
    onFilterAllClick: () -> Unit,
    onFilterOnlyRemoteClick: () -> Unit,
    onFilterOnlyLocaleClick: () -> Unit,
    onAddClick: () -> Unit = {},
    scaffoldState: ScaffoldState,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.listing_title)) },
                actions = {
                    FilterMenu(
                        onFilterAllClick = onFilterAllClick,
                        onFilterOnlyRemoteClick = onFilterOnlyRemoteClick,
                        onFilterOnlyLocaleClick = onFilterOnlyLocaleClick
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = null
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val state by viewModel.state.flow.collectAsState()
        val loading by viewModel.loading.flow.collectAsState()

        val insetsModifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)

        ListingContent(
            state = state,
            loading = loading,
            onRefresh = onRefresh,
            modifier = insetsModifier
        )
    }
}

@Composable
private fun ListingContent(
    state: ListingViewModel.State,
    loading: Boolean,
    onRefresh: () -> Unit = {},
    modifier: Modifier
) {
    LoadingContent(
        loading = loading,
        onRefresh = onRefresh,
        modifier = modifier
    ) {
        when (state) {
            is ListingViewModel.State.Success -> ResultListingItems(state.items)
            is ListingViewModel.State.Error -> LoadErrorContent(modifier)
            is ListingViewModel.State.Empty -> EmptyContent(modifier)
            else -> EmptyContent(modifier)
        }
    }
}