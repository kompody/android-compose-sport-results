package com.kompody.etnetera.data.database.mapper

import com.kompody.etnetera.data.database.model.ResultDbModel
import com.kompody.etnetera.domain.entity.ResultModel

fun ResultDbModel.toModel() = ResultModel(
    name = name,
    place = place,
    duration = duration,
    date = date,
    type = ResultModel.Type.valueOf(type)
)

fun ResultModel.toDbModel() = ResultDbModel(
    name = name,
    place = place,
    duration = duration,
    date = date,
    type = type.name
)