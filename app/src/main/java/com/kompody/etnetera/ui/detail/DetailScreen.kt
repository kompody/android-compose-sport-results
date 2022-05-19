@file:OptIn(InternalCoroutinesApi::class)

package com.kompody.etnetera.ui.detail

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
import com.kompody.etnetera.ui.listing.compose.ResultItemContent
import com.kompody.etnetera.ui.theme.MainTheme
import com.kompody.etnetera.ui.view.EmptyContent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect

@Composable
fun DetailScreen(
    onBackPressed: () -> Unit = {}
) {
    val viewModel: DetailViewModel = hiltViewModel()

    LaunchedEffect(true) {
        viewModel.commands.flow.collect { command ->
            when (command) {
                is DetailViewModel.Command.CloseScreen -> onBackPressed()
            }
        }
    }

    MainTheme {
        DetailContent(
            viewModel = viewModel
        )
    }
}

@Composable
fun DetailContent(
    viewModel: DetailViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.detail_title)) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.accept(DetailViewModel.Action.BackPressed) }) {
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

        val insetsModifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)

        if (state.resultItem != null) {
            ResultItemContent(item = state.resultItem!!)
        } else {
            EmptyContent(modifier = insetsModifier)
        }
    }
}