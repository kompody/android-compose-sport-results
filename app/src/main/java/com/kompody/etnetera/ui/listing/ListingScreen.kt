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
import com.kompody.etnetera.ui.listing.compose.FilterMenu
import com.kompody.etnetera.ui.listing.compose.ResultListingItems
import com.kompody.etnetera.ui.listing.model.ResultItem
import com.kompody.etnetera.ui.theme.MainTheme
import com.kompody.etnetera.ui.view.EmptyContent
import com.kompody.etnetera.ui.view.LoadErrorContent
import com.kompody.etnetera.ui.view.LoadingContent
import com.kompody.etnetera.ui.view.PlaceholderContent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect

@Composable
fun ListingScreen(
    openAddResultScreen: () -> Unit = {},
    openDetailScreen: (ResultItem) -> Unit = {}
) {
    val viewModel: ListingViewModel = hiltViewModel()
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    LaunchedEffect(true) {
        viewModel.commands.flow.collect { command ->
            when (command) {
                is ListingViewModel.Command.OpenAddResultScreen -> {
                    openAddResultScreen()
                }
                is ListingViewModel.Command.OpenListingItem -> {
                    openDetailScreen(command.item)
                }
            }
        }
    }

    MainTheme {
        ListingScreen(
            onRefresh = { viewModel.accept(ListingViewModel.Action.Refresh) },
            onFilterAllClick = { viewModel.accept(ListingViewModel.Action.FilterAllClick) },
            onFilterOnlyRemoteClick = { viewModel.accept(ListingViewModel.Action.FilterOnlyRemote) },
            onFilterOnlyLocaleClick = { viewModel.accept(ListingViewModel.Action.FilterOnlyLocale) },
            onResultItemClick = { viewModel.accept(ListingViewModel.Action.ListingItemClick(it)) },
            onAddClick = { viewModel.accept(ListingViewModel.Action.AddButtonClick) },
            viewModel = viewModel,
            scaffoldState = scaffoldState
        )
    }
}

@Composable
private fun ListingScreen(
    onRefresh: () -> Unit = {},
    onFilterAllClick: () -> Unit = {},
    onFilterOnlyRemoteClick: () -> Unit = {},
    onFilterOnlyLocaleClick: () -> Unit = {},
    onResultItemClick: (ResultItem) -> Unit = {},
    onAddClick: () -> Unit = {},
    viewModel: ListingViewModel,
    scaffoldState: ScaffoldState
) {
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
                    onRefresh()
                }
            }
        }
    }

    ListingContent(
        onRefresh = onRefresh,
        onFilterAllClick = onFilterAllClick,
        onFilterOnlyRemoteClick = onFilterOnlyRemoteClick,
        onFilterOnlyLocaleClick = onFilterOnlyLocaleClick,
        onResultItemClick = onResultItemClick,
        onAddClick = onAddClick,
        viewModel = viewModel,
        scaffoldState = scaffoldState
    )
}

@Composable
private fun ListingContent(
    onRefresh: () -> Unit = {},
    onFilterAllClick: () -> Unit = {},
    onFilterOnlyRemoteClick: () -> Unit = {},
    onFilterOnlyLocaleClick: () -> Unit = {},
    onResultItemClick: (ResultItem) -> Unit = {},
    onAddClick: () -> Unit = {},
    viewModel: ListingViewModel,
    scaffoldState: ScaffoldState
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
            onRefresh = onRefresh,
            onResultItemClick = onResultItemClick,
            loading = loading,
            state = state,
            modifier = insetsModifier
        )
    }
}

@Composable
private fun ListingContent(
    onRefresh: () -> Unit = {},
    onResultItemClick: (ResultItem) -> Unit = {},
    loading: Boolean,
    state: ListingViewModel.State,
    modifier: Modifier
) {
    LoadingContent(
        loading = loading,
        onRefresh = onRefresh,
        modifier = modifier
    ) {
        when (state) {
            is ListingViewModel.State.Success -> ResultListingItems(
                onResultItemClick = onResultItemClick,
                items = state.items
            )
            is ListingViewModel.State.Error -> LoadErrorContent(modifier)
            is ListingViewModel.State.Empty -> EmptyContent(modifier)
            else -> PlaceholderContent(modifier)
        }
    }
}