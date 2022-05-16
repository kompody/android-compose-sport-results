package com.kompody.etnetera.utils

import com.kompody.etnetera.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

const val CONNECTION_TIMEOUT = 25L //10
const val CONNECTION_READ_WRITE_TIMEOUT = 300L

fun buildOkHttpClient(
    builderAction: OkHttpClient.Builder.() -> Unit = {}
): OkHttpClient = with(OkHttpClient.Builder()) {
    connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
    readTimeout(CONNECTION_READ_WRITE_TIMEOUT, TimeUnit.SECONDS)
    writeTimeout(CONNECTION_READ_WRITE_TIMEOUT, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
        addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    }

    builderAction()
    build()
}