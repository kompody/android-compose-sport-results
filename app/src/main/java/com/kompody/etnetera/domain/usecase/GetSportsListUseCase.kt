package com.kompody.etnetera.domain.usecase

import com.kompody.etnetera.domain.repository.SportsListRepository
import javax.inject.Inject

class GetSportsListUseCase @Inject constructor(
    private val sportListRepository: SportsListRepository
) {
    suspend fun execute() = sportListRepository.loadSportList()
}