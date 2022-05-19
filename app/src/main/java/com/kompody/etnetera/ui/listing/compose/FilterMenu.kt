package com.kompody.etnetera.ui.listing.compose

import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.kompody.etnetera.R
import com.kompody.etnetera.ui.view.TopAppBarDropdownMenu

@Composable
fun FilterMenu(
    onFilterAllClick: () -> Unit,
    onFilterOnlyRemoteClick: () -> Unit,
    onFilterOnlyLocaleClick: () -> Unit
) {
    TopAppBarDropdownMenu(
        iconContent = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_menu_24),
                contentDescription = null
            )
        }
    ) { closeMenu ->
        DropdownMenuItem(onClick = { onFilterAllClick(); closeMenu() }) {
            Text(text = stringResource(id = R.string.listing_filter_all))
        }
        DropdownMenuItem(onClick = { onFilterOnlyRemoteClick(); closeMenu() }) {
            Text(text = stringResource(id = R.string.listing_filter_only_remote))
        }
        DropdownMenuItem(onClick = { onFilterOnlyLocaleClick(); closeMenu() }) {
            Text(text = stringResource(id = R.string.listing_filter_only_locale))
        }
    }
}