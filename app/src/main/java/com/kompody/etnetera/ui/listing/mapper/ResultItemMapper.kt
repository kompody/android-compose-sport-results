package com.kompody.etnetera.ui.listing.mapper

import com.kompody.etnetera.domain.entity.ResultModel
import com.kompody.etnetera.ui.listing.model.ResultItem

fun ResultModel.toViewModel() = when (type) {
    ResultModel.Type.Remote -> {
        ResultItem.RemoteResultItem(
            name = name,
            place = place.toString(),
            duration = duration.toString(),
            date = date.toString(),
        )
    }
    ResultModel.Type.Locale -> {
        ResultItem.LocaleResultItem(
            name = name,
            place = place.toString(),
            duration = duration.toString(),
            date = date.toString(),
        )
    }
}