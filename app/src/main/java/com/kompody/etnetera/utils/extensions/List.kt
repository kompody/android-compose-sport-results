package com.kompody.etnetera.utils.extensions

fun <T> concatenate(vararg lists: List<T>): List<T> {
    return listOf(*lists).flatten()
}