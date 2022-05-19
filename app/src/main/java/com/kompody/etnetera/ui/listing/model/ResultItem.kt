package com.kompody.etnetera.ui.listing.model

import java.io.Serializable

sealed class ResultItem : Serializable {
    class RemoteResultItem(
        val name: String,
        val place: String,
        val duration: String,
        val date: String
    ) : ResultItem()

    class LocaleResultItem(
        val name: String,
        val place: String,
        val duration: String,
        val date: String
    ) : ResultItem()
}