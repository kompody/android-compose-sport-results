package com.kompody.etnetera.ui.listing.view.listing

import androidx.compose.runtime.Composable
import com.kompody.etnetera.ui.listing.model.ResultItem

@Composable
fun ResultItemContent(item: ResultItem) {
    when (item) {
        is ResultItem.RemoteResultItem -> ResultItemRemoteContent(item)
        is ResultItem.LocaleResultItem -> ResultItemLocaleContent(item)
    }
}