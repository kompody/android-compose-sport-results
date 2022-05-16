package com.kompody.etnetera.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightThemeColors = lightColors(
//    primary = Color(0xFFBA9E31),
//    primaryVariant = Color(0x99FFFFFF),
//    secondary = Color(0xFF000000),
)

private val DarkThemeColors = darkColors(
//    primary = Color(0xFFBA9E31),
//    primaryVariant = Color(0x99FFFFFF),
//    secondary = Color(0xFFFFFFFF),
//    background = Color(0xFF000000),
//    onBackground = Color(0xFFD9D9D9)
)

@Composable
fun MainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Color.Black,
        darkIcons = !darkTheme
    )

    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = Typography,
        shapes = Shapes
    ) {
        ProvideWindowInsets {
            val insetsPadding = rememberInsetsPaddingValues(
                LocalWindowInsets.current.statusBars,
                applyBottom = false,
            )
            Box(
                modifier = Modifier
                    .padding(insetsPadding)
            ) {
                content()
            }
        }
    }
}

@Composable
fun LeafTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Color.Black,
        darkIcons = !darkTheme
    )

    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = Typography,
        shapes = Shapes
    ) {
        Box {
            content()
        }
    }
}

@Composable
fun FullScreenTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    statusBarColor: Color = Color.Transparent,
    statusBarDarkIcons: Boolean = false,
    content: @Composable () -> Unit
) {
    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = statusBarColor,
        darkIcons = statusBarDarkIcons
    )

    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = Typography,
        shapes = Shapes
    ) {
        ProvideWindowInsets {
            val insets = LocalWindowInsets.current

            val imeBottom = with(LocalDensity.current) { insets.ime.bottom.toDp() }
            Box(
                modifier = Modifier
                    .padding(bottom = imeBottom)
            ) {
                content()
            }
        }
    }
}