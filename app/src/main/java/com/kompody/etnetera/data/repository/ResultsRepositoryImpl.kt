package com.kompody.etnetera.data.repository

import com.kompody.etnetera.data.database.dao.ResultDao
import com.kompody.etnetera.data.database.mapper.toModel
import com.kompody.etnetera.data.database.model.ResultDbModel
import com.kompody.etnetera.data.network.api.ResultApi
import com.kompody.etnetera.data.network.mapper.toModel
import com.kompody.etnetera.data.network.model.ResultApiModel
import com.kompody.etnetera.domain.entity.ResultModel
import com.kompody.etnetera.domain.repository.ResultsRepository
import com.kompody.etnetera.ui.listing.model.ResultItemFilter
import com.kompody.etnetera.utils.extensions.concatenate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ResultsRepositoryImpl @Inject constructor(
    private val resultApi: ResultApi,
    private val resultDao: ResultDao
) : ResultsRepository {

    override suspend fun fetchResults(filter: ResultItemFilter): Flow<List<ResultModel>> {
        return resultDao.flowResults().map { it.map(ResultDbModel::toModel) }
    }

    override suspend fun getResults(filter: ResultItemFilter): List<ResultModel> {
        val apiItems: List<ResultModel> = try {
            resultApi.fetchSportList(filter.name).map(ResultApiModel::toModel)
        } catch (e: Exception) {
            listOf()
        }

        val daoItems: List<ResultModel> = try {
            resultDao.getResults().map(ResultDbModel::toModel)
        } catch (e: Exception) {
            listOf()
        }

        return concatenate(
            apiItems,
            daoItems,
        )
    }
}