package com.kompody.etnetera.data.network.mapper

import com.kompody.etnetera.data.network.model.ResultApiModel
import com.kompody.etnetera.domain.entity.ResultModel

fun ResultApiModel.toModel() = ResultModel(
    name = name,
    place = place,
    duration = duration,
    date = date,
    type = ResultModel.Type.valueOf(type)
)