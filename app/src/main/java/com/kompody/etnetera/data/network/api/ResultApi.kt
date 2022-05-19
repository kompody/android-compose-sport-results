package com.kompody.etnetera.data.network.api

import com.kompody.etnetera.data.network.model.Base
import com.kompody.etnetera.data.network.model.ResultApiModel
import com.kompody.etnetera.data.network.model.ResultRequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface ResultApi {
    @GET("list/results")
    suspend fun fetchResultsList(): Base<List<ResultApiModel>>

    @PUT("put/result")
    suspend fun putResult(@Body body: ResultRequestBody): Base<Unit>
}