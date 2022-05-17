package com.kompody.etnetera.data.network.api

import com.kompody.etnetera.data.network.model.ResultApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ResultApi {
    @GET("list/results")
    suspend fun fetchSportList(@Query("filter") filter: String) : List<ResultApiModel>
}