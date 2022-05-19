package com.kompody.etnetera.domain.usecase

import com.kompody.etnetera.domain.entity.ResultModel
import com.kompody.etnetera.domain.repository.ResultsRepository
import com.kompody.etnetera.ui.listing.mapper.toViewModel
import com.kompody.etnetera.ui.listing.model.ResultItem
import com.kompody.etnetera.ui.listing.model.ResultItemFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchResultsUseCase @Inject constructor(
    private val resultsRepository: ResultsRepository
) {
    suspend fun flowLocaleResults(): Flow<List<ResultItem>> {
        return resultsRepository.fetchResults()
            .map { it.map(ResultModel::toViewModel) }
    }

    suspend fun execute(filter: ResultItemFilter): List<ResultItem> {
        return resultsRepository.getResults(filter)
            .sortedByDescending { it.date }
            .map(ResultModel::toViewModel)
    }
}