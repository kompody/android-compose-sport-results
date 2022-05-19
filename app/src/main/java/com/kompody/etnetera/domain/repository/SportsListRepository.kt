package com.kompody.etnetera.domain.repository

import com.kompody.etnetera.domain.entity.SportModel

interface SportsListRepository {
    suspend fun loadSportList(): List<SportModel>
}