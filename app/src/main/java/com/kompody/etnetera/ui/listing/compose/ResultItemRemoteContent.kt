package com.kompody.etnetera.ui.listing.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kompody.etnetera.ui.listing.model.ResultItem
import com.kompody.etnetera.ui.theme.ListingItemRemoteBackground
import com.kompody.etnetera.ui.theme.ListingItemRemoteIndicatorTextColor

@Composable
fun ResultItemRemoteContent(item: ResultItem.RemoteResultItem) {
    ResultItemBaseContent(
        name = item.name,
        place = item.place,
        duration = item.duration,
        date = item.date,
        background = Color.ListingItemRemoteBackground,
        indicatorText = "remote",
        indicatorColor = Color.ListingItemRemoteIndicatorTextColor
    )
}