package com.kompody.etnetera.di

import com.kompody.etnetera.data.database.dao.ResultDao
import com.kompody.etnetera.data.network.api.ResultApi
import com.kompody.etnetera.data.repository.ResultsRepositoryImpl
import com.kompody.etnetera.domain.repository.ResultsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideResultsRepository(resultApi: ResultApi, resultDao: ResultDao): ResultsRepository =
        ResultsRepositoryImpl(resultApi, resultDao)
}