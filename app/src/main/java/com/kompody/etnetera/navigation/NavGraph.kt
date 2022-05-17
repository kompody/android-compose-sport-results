package com.kompody.etnetera.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kompody.etnetera.ui.listing.ListingScreen
import com.kompody.etnetera.ui.splash.SplashScreen
import kotlinx.coroutines.InternalCoroutinesApi

sealed class Screen(val route: String) {
    object Splash : Screen("splash")

    object Main : Screen("main")
}

sealed class LeafScreen(
    private val route: String,
) {
    fun createRoute(root: Screen) = "${root.route}/$route"

    object Splash : LeafScreen("splash")
    object Listing : LeafScreen("listing")
}

@InternalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        addSplashFlow(navController)
        addMainFlow()
    }
}

@InternalCoroutinesApi
fun NavGraphBuilder.addSplashFlow(
    navController: NavController,
) {
    navigation(
        route = Screen.Splash.route,
        startDestination = LeafScreen.Splash.createRoute(Screen.Splash)
    ) {
        addSplash(navController, Screen.Splash)
    }
}

@InternalCoroutinesApi
fun NavGraphBuilder.addSplash(
    navController: NavController,
    root: Screen
) {
    composable(
        route = LeafScreen.Splash.createRoute(root)
    ) {
        SplashScreen(
            openMainScreen = {
                navController.navigate(Screen.Main.route) {
                    popUpTo(Screen.Splash.route) {
                        inclusive = true
                    }
                    launchSingleTop = false
                    restoreState = false
                }
            }
        )
    }
}

@InternalCoroutinesApi
@ExperimentalMaterialApi
fun NavGraphBuilder.addMainFlow() {
    navigation(
        route = Screen.Main.route,
        startDestination = LeafScreen.Listing.createRoute(Screen.Main)
    ) {
        addListing(Screen.Main)
    }
}

@InternalCoroutinesApi
@ExperimentalMaterialApi
fun NavGraphBuilder.addListing(
    root: Screen
) {
    composable(
        route = LeafScreen.Listing.createRoute(root)
    ) {
        ListingScreen()
    }
}