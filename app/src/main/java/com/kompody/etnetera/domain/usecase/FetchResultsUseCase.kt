package com.kompody.etnetera.domain.usecase

import com.kompody.etnetera.domain.entity.ResultModel
import com.kompody.etnetera.domain.repository.ResultsRepository
import com.kompody.etnetera.ui.listing.mapper.toViewModel
import com.kompody.etnetera.ui.listing.model.ResultItem
import com.kompody.etnetera.ui.listing.model.ResultItemFilter
import javax.inject.Inject

class FetchResultsUseCase @Inject constructor(
    private val resultsRepository: ResultsRepository
) {

    private val allItems = listOf(
        ResultItem.RemoteResultItem(
            name = "Running",
            place = "1",
            duration = "0:56",
            date = "13 may 2022",
        ),
        ResultItem.RemoteResultItem(
            name = "Running",
            place = "2",
            duration = "1:16",
            date = "12 may 2022",
        ),
        ResultItem.LocaleResultItem(
            name = "Basketball",
            place = "1",
            duration = "0:56",
            date = "11 may 2022",
        ),
        ResultItem.RemoteResultItem(
            name = "Running2",
            place = "21",
            duration = "1:16",
            date = "10 may 2022",
        ),
        ResultItem.LocaleResultItem(
            name = "Basketball2",
            place = "11",
            duration = "0:56",
            date = "9 may 2022",
        )
    )

    suspend fun execute(filter: ResultItemFilter): List<ResultItem> {
        return resultsRepository.getResults(filter)
            .map(ResultModel::toViewModel)
    }
}