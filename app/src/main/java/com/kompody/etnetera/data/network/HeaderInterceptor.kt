package com.kompody.etnetera.data.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var newRequest: Request = chain.request()

        newRequest = newRequest.newBuilder()
            .build()

        return chain.proceed(newRequest)
    }
}