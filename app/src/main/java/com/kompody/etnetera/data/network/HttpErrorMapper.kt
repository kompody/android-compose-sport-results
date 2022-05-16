package com.kompody.etnetera.data.network

import com.google.gson.Gson
import com.kompody.etnetera.data.network.model.HttpErrorBody
import com.kompody.etnetera.utils.extensions.fromJsonOrNull
import javax.inject.Inject

class HttpErrorMapper @Inject constructor(
    private val gson: Gson
) {
    fun map(rawString: String): HttpErrorBody? {
        return gson.fromJsonOrNull(rawString)
    }
}