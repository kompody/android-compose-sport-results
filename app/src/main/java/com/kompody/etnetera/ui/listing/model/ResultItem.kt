package com.kompody.etnetera.ui.listing.model

sealed class ResultItem {
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