package com.kompody.etnetera.domain.repository

import com.kompody.etnetera.domain.entity.ResultModel
import com.kompody.etnetera.ui.listing.model.ResultItemFilter
import kotlinx.coroutines.flow.Flow

interface ResultsRepository {
    suspend fun fetchResults(filter: ResultItemFilter): Flow<List<ResultModel>>
    suspend fun getResults(filter: ResultItemFilter): List<ResultModel>
}