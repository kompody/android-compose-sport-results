@file:OptIn(ExperimentalFoundationApi::class)

package com.kompody.etnetera.ui.listing.view.listing

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kompody.etnetera.ui.listing.model.ResultItem

@Composable
fun ResultListingItems(items: List<ResultItem>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxSize()
    ) {
        items(items = items, key = { it.hashCode() }) { item ->
            ResultItemContent(
                item = item,
                modifier = Modifier
                    .clickable { items.shuffled() }
                    .animateItemPlacement()
            )
        }
    }
}

@Composable
private fun ResultItemContent(
    item: ResultItem,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        ResultItemContent(item)
    }
}