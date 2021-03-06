package com.kompody.etnetera.utils.extensions

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.fromJson(json: String): T =
    this.fromJson<T>(json, object: TypeToken<T>() {}.type)

inline fun <reified T> Gson.fromJsonOrNull(json: String): T? =
    try {
        this.fromJson<T>(json, object: TypeToken<T>() {}.type)
    } catch (e: JsonSyntaxException) {
        null
    }