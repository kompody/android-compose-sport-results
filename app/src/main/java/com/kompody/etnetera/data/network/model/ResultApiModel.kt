package com.kompody.etnetera.data.network.model

data class ResultApiModel(
    val id: Long = 0,
    val sportId: Int,
    val sportName: String,
    val place: Int,
    val duration: Int,
    val type: String,
    val date: Long
)
