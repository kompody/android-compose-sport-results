package com.kompody.etnetera.ui.listing.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kompody.etnetera.ui.listing.model.ResultItem
import com.kompody.etnetera.ui.theme.ListingItemLocaleBackground
import com.kompody.etnetera.ui.theme.ListingItemLocaleIndicatorTextColor

@Composable
fun ResultItemLocaleContent(item: ResultItem) {
    ResultItemBaseContent(
        sportName = item.sportName,
        place = item.place,
        duration = item.duration,
        date = item.date,
        background = Color.ListingItemLocaleBackground,
        indicatorText = "locale",
        indicatorColor = Color.ListingItemLocaleIndicatorTextColor
    )
}