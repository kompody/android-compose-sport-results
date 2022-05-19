package com.kompody.etnetera.ui.listing.compose

import androidx.compose.runtime.Composable
import com.kompody.etnetera.domain.entity.ResultModel
import com.kompody.etnetera.ui.listing.model.ResultItem

@Composable
fun ResultItemContent(item: ResultItem) {
    when (item.type) {
        ResultModel.Type.Remote -> ResultItemRemoteContent(item)
        ResultModel.Type.Locale -> ResultItemLocaleContent(item)
    }
}