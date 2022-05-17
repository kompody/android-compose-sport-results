@file:OptIn(InternalCoroutinesApi::class)

package com.kompody.etnetera.ui.listing

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
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
fun ListingScreen(
    flag: Boolean = true
) {
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
            viewModel = viewModel
        )
    }
}

@Composable
private fun ListingScreen(
    viewModel: ListingViewModel,
) {
    ListingContent(
        viewModel = viewModel,
        onRefresh = { viewModel.accept(ListingViewModel.Action.Refresh) },
        onFilterAllClick = { viewModel.accept(ListingViewModel.Action.FilterAllClick) },
        onFilterOnlyRemoteClick = { viewModel.accept(ListingViewModel.Action.FilterOnlyRemote) },
        onFilterOnlyLocaleClick = { viewModel.accept(ListingViewModel.Action.FilterOnlyLocale) },
        onAddClick = { viewModel.accept(ListingViewModel.Action.AddButtonClick) }
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
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val (showSnackBar, setShowSnackBar) = remember {
        mutableStateOf(false)
    }
    val error by viewModel.errors.flow.collectAsState(null)

    if (showSnackBar) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = error.toString()
            )
            when (result) {
                SnackbarResult.Dismissed -> {
                    setShowSnackBar(false)
                }
                SnackbarResult.ActionPerformed -> {
                    setShowSnackBar(false)
                    // perform action here
                }
            }
        }
    }

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