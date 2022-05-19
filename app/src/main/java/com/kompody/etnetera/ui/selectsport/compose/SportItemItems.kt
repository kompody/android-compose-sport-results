package com.kompody.etnetera.ui.selectsport.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kompody.etnetera.ui.selectsport.model.SportItem

@Composable
fun SportItemItems(
    onSportItemClick: (SportItem) -> Unit = {},
    items: List<SportItem>
) {
    LazyColumn(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxSize()
    ) {
        items(items = items) { item ->
            SportItemContent(
                item = item,
                modifier = Modifier
                    .clickable { onSportItemClick(item) }
            )
        }
    }
}

@Composable
private fun SportItemContent(
    item: SportItem,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        SportItemContent(item = item)
    }
}