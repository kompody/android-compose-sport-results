package com.kompody.etnetera.data.network.mapper

import com.kompody.etnetera.data.network.model.ResultApiModel
import com.kompody.etnetera.data.network.model.ResultRequestBody
import com.kompody.etnetera.domain.entity.ResultModel

fun ResultApiModel.toModel() = ResultModel(
    id = id,
    sportId = sportId,
    sportName = sportName,
    place = place,
    duration = duration,
    date = date,
    type = ResultModel.Type.valueOf(type)
)

fun ResultModel.toRequestBody() = ResultRequestBody(
    sportId = sportId,
    place = place,
    duration = duration
)