package com.kompody.etnetera.domain.usecase

import com.kompody.etnetera.domain.entity.ResultModel
import com.kompody.etnetera.domain.repository.ResultsRepository
import javax.inject.Inject

class PutResultToLocaleUseCase @Inject constructor(
    private val resultsRepository: ResultsRepository
) {
    suspend fun execute(model: ResultModel): Boolean {
        return resultsRepository.putResultItemToLocale(model)
    }
}