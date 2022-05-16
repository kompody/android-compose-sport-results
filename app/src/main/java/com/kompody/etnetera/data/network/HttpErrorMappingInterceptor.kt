package com.kompody.etnetera.data.network

import com.kompody.etnetera.data.network.mock.getErrorMock
import com.kompody.etnetera.domain.error.AppException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HttpErrorMappingInterceptor @Inject constructor(
    private val errorMapper: HttpErrorMapper
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.code in 400..500) {
            errorMapper.map(getErrorMock)?.also { errorBody ->
                if (errorBody.state >= 400) {
                    throw AppException.Api.Error(errorBody.message)
                } else {
                    return@also
                }
            }
        }
        return response
    }
}
