package com.kompody.etnetera.ui.splash

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kompody.etnetera.R
import com.kompody.etnetera.ui.theme.FullScreenTheme
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect

@InternalCoroutinesApi
@Composable
fun SplashScreen(
    openMainScreen: () -> Unit
) {
    val viewModel: SplashViewModel = hiltViewModel()

    LaunchedEffect(true) {
        viewModel.commands.flow.collect { command ->
            when (command) {
                is SplashViewModel.Command.OpenMainScreen -> openMainScreen.invoke()
            }
        }
    }

    FullScreenTheme(
        statusBarColor = Color.Transparent,
        statusBarDarkIcons = false
    ) {
        SplashScreen()
    }
}

@Composable
private fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.subtitle2,
                fontSize = 32.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(
                modifier = Modifier
                    .height(132.dp)
            )

            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}