package com.kompody.etnetera.data.network.api

import com.kompody.etnetera.data.network.model.Base
import com.kompody.etnetera.data.network.model.SportApiModel
import retrofit2.http.GET

interface SportsListApi {
    @GET("list/sports")
    suspend fun fetchSportList() : Base<List<SportApiModel>>
}