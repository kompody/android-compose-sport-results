package com.kompody.etnetera.data.repository

import com.kompody.etnetera.data.network.api.SportsListApi
import com.kompody.etnetera.domain.entity.SportModel
import com.kompody.etnetera.domain.repository.SportsListRepository
import javax.inject.Inject

class SportsListRepositoryImpl @Inject constructor(
    private val sportsListApi: SportsListApi
): SportsListRepository {

    override suspend fun loadSportList(): List<SportModel> {
        return sportsListApi.fetchSportList().result
            .map { model ->
                SportModel(
                    id = model.id,
                    name = model.name
                )
            }
    }
}