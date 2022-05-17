package com.kompody.etnetera.data.network

import com.kompody.etnetera.BuildConfig
import com.kompody.etnetera.data.network.mock.fetchSportListMock
import com.kompody.etnetera.data.network.mock.getErrorMock
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.util.*
import javax.inject.Inject

class MockInterceptor @Inject constructor(
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!BuildConfig.DEBUG) {
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and bound to be used only with DEBUG mode"
            )
        }

        chain.request().url.toUri().toString()

        val uri = chain.request().url.pathSegments.joinToString("/")
        val responseString = when {
            uri.endsWith("list/results") -> fetchSportListMock
            else -> ""
        }

        val isErrorChance = Date().time.toInt() % 4 == 0 //25%
        if (isErrorChance) {
            val errorString = getErrorMock

            return chain.proceed(chain.request())
                .newBuilder()
                .code(400)
                .protocol(Protocol.HTTP_2)
                .message(errorString)
                .body(
                    errorString.toByteArray()
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
                .addHeader("content-type", "application/json")
                .build()
        }

        return chain.proceed(chain.request())
            .newBuilder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message(responseString)
            .body(
                responseString.toByteArray()
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
            .addHeader("content-type", "application/json")
            .build()
    }
}