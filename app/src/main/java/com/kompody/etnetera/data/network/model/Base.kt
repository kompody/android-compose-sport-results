package com.kompody.etnetera.data.network.model

data class Base<Result>(
    val state: String,
    val message: String,
    val result: Result
)