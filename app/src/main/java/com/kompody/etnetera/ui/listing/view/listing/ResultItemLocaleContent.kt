package com.kompody.etnetera.ui.listing.view.listing

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kompody.etnetera.ui.listing.model.ResultItem
import com.kompody.etnetera.ui.theme.ListingItemLocaleBackground
import com.kompody.etnetera.ui.theme.ListingItemLocaleIndicatorTextColor

@Composable
fun ResultItemLocaleContent(item: ResultItem.LocaleResultItem) {
    ResultItemBaseContent(
        name = item.name,
        place = item.place,
        duration = item.duration,
        date = item.date,
        background = Color.ListingItemLocaleBackground,
        indicatorText = "locale",
        indicatorColor = Color.ListingItemLocaleIndicatorTextColor
    )
}