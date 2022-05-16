@file:OptIn(InternalCoroutinesApi::class, ExperimentalMaterialApi::class)

package com.kompody.etnetera.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.savedstate.ViewTreeSavedStateRegistryOwner
import com.kompody.etnetera.navigation.SetupNavGraph
import com.kompody.etnetera.ui.base.receivers.CloseApplicationReceiver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var navController: NavHostController

    @Inject
    lateinit var closeApplicationReceiver: CloseApplicationReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setContent {
            navController = rememberNavController()

            SetupNavGraph(navController = navController)
        }

        setOwners()

        lifecycleScope.launchWhenCreated {
            closeApplicationReceiver.events.collect {
                finish()
            }
        }
    }

    private fun ComponentActivity.setOwners() {
        val decorView = window.decorView
        if (ViewTreeLifecycleOwner.get(decorView) == null) {
            ViewTreeLifecycleOwner.set(decorView, this)
        }
        if (ViewTreeViewModelStoreOwner.get(decorView) == null) {
            ViewTreeViewModelStoreOwner.set(decorView, this)
        }
        if (ViewTreeSavedStateRegistryOwner.get(decorView) == null) {
            ViewTreeSavedStateRegistryOwner.set(decorView, this)
        }
    }
}