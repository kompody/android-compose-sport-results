package com.kompody.etnetera.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.gson.Gson
import com.kompody.etnetera.ui.addresult.AddResultScreen
import com.kompody.etnetera.ui.detail.DetailScreen
import com.kompody.etnetera.ui.listing.ListingScreen
import com.kompody.etnetera.ui.listing.model.ResultItem
import com.kompody.etnetera.ui.selectsport.SelectSportScreen
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
    object Detail : LeafScreen("detail/{item}") {
        fun createRoute(root: Screen, item: ResultItem): String {
            return "${root.route}/detail/${Gson().toJson(item)}"
        }
    }

    object AddResult : LeafScreen("add_result")
    object SelectSport : LeafScreen("select_sport")
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
        addMainFlow(navController)
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
fun NavGraphBuilder.addMainFlow(
    navController: NavController
) {
    navigation(
        route = Screen.Main.route,
        startDestination = LeafScreen.Listing.createRoute(Screen.Main)
    ) {
        addListing(navController, Screen.Main)
        addAddResult(navController, Screen.Main)
        addDetail(navController, Screen.Main)
        addSelectSport(navController, Screen.Main)
    }
}

@InternalCoroutinesApi
@ExperimentalMaterialApi
fun NavGraphBuilder.addListing(
    navController: NavController,
    root: Screen
) {
    composable(
        route = LeafScreen.Listing.createRoute(root)
    ) {
        ListingScreen(
            openAddResultScreen = {
                navController.navigate(LeafScreen.AddResult.createRoute(root))
            },
            openDetailScreen = { item ->
                navController.navigate(LeafScreen.Detail.createRoute(root, item))
            }
        )
    }
}

@InternalCoroutinesApi
@ExperimentalMaterialApi
fun NavGraphBuilder.addAddResult(
    navController: NavController,
    root: Screen
) {
    composable(
        route = LeafScreen.AddResult.createRoute(root)
    ) {
        AddResultScreen(
            onBackPressed = {
                navController.navigateUp()
            },
            openSelectSportScreen = {
                navController.navigate(LeafScreen.SelectSport.createRoute(root))
            }
        )
    }
}

@InternalCoroutinesApi
@ExperimentalMaterialApi
fun NavGraphBuilder.addDetail(
    navController: NavController,
    root: Screen
) {
    composable(
        route = LeafScreen.Detail.createRoute(root),
        arguments = listOf(
            navArgument("item") { type = NavType.StringType }
        )
    ) {
//        val resultItem: ResultItem = it.arguments?.getSerializable("resultItem") as ResultItem
        DetailScreen(
            onBackPressed = {
                navController.navigateUp()
            }
        )
    }
}

@InternalCoroutinesApi
@ExperimentalMaterialApi
fun NavGraphBuilder.addSelectSport(
    navController: NavController,
    root: Screen
) {
    composable(
        route = LeafScreen.SelectSport.createRoute(root)
    ) {
        SelectSportScreen(
            onBackPressed = {
                navController.navigateUp()
            }
        )
    }
}