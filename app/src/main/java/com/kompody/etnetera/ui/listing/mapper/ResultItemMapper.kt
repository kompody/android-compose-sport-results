package com.kompody.etnetera.ui.listing.mapper

import com.kompody.etnetera.domain.entity.ResultModel
import com.kompody.etnetera.ui.listing.model.ResultItem
import com.kompody.etnetera.utils.extensions.DateFormat
import com.kompody.etnetera.utils.extensions.calendarOf
import com.kompody.etnetera.utils.extensions.toString
import java.util.*

fun ResultModel.toViewModel() = when (type) {
    ResultModel.Type.Remote -> {
        ResultItem.RemoteResultItem(
            name = sportName,
            place = place.toString(),
            duration = calendarOf(duration.toLong() - TimeZone.getDefault().rawOffset).time.toString(DateFormat.Seconds),
            date = calendarOf(date).time.toString(DateFormat.FullSet).replace(" ", "\n")
        )
    }
    ResultModel.Type.Locale -> {
        ResultItem.LocaleResultItem(
            name = sportName,
            place = place.toString(),
            duration = calendarOf(duration.toLong() - TimeZone.getDefault().rawOffset).time.toString(DateFormat.Seconds),
            date = calendarOf(date).time.toString(DateFormat.FullSet).replace(" ", "\n")
        )
    }
}