package com.kompody.etnetera.ui.listing.model

import com.kompody.etnetera.domain.entity.ResultModel

class ResultItem(
    val id: Long = 0,
    val sportId: Int,
    val sportName: String,
    val place: String,
    val duration: String,
    val date: String,
    val type: ResultModel.Type
)