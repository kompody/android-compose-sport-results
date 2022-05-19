package com.kompody.etnetera.data.repository

import com.kompody.etnetera.data.database.dao.ResultDao
import com.kompody.etnetera.data.database.mapper.toDbModel
import com.kompody.etnetera.data.database.mapper.toModel
import com.kompody.etnetera.data.database.model.ResultDbModel
import com.kompody.etnetera.data.network.api.ResultApi
import com.kompody.etnetera.data.network.mapper.toModel
import com.kompody.etnetera.data.network.mapper.toRequestBody
import com.kompody.etnetera.data.network.model.ResultApiModel
import com.kompody.etnetera.domain.entity.ResultModel
import com.kompody.etnetera.domain.repository.ResultsRepository
import com.kompody.etnetera.ui.listing.model.ResultItemFilter
import com.kompody.etnetera.utils.extensions.concatenate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ResultsRepositoryImpl @Inject constructor(
    private val resultApi: ResultApi,
    private val resultDao: ResultDao
) : ResultsRepository {

    override suspend fun fetchResults(): Flow<List<ResultModel>> {
        return resultDao.flowResults().distinctUntilChanged().map { it.map(ResultDbModel::toModel) }
    }

    override suspend fun getResults(filter: ResultItemFilter): List<ResultModel> {
        val (apiItems, daoItems) = when (filter) {
            ResultItemFilter.All -> {
                Pair(fetchResultsRemote(), fetchRemoteLocale())
            }
            ResultItemFilter.OnlyRemote -> {
                Pair(fetchResultsRemote(), listOf())
            }
            ResultItemFilter.OnlyLocale -> {
                Pair(listOf(), fetchRemoteLocale())
            }
        }

        return concatenate(
            apiItems,
            daoItems,
        )
    }

    override suspend fun putResultItemToRemote(model: ResultModel) {
        resultApi.putResult(model.toRequestBody())
    }

    override suspend fun putResultItemToLocale(model: ResultModel): Boolean {
        return resultDao.insertResult(model.toDbModel()) > 0
    }

    private suspend fun fetchRemoteLocale(): List<ResultModel> {
        return resultDao.getResults().map(ResultDbModel::toModel)
    }

    private suspend fun fetchResultsRemote(): List<ResultModel> {
        return resultApi.fetchResultsList().result.map(ResultApiModel::toModel)
    }
}