package com.kompody.etnetera.di

import com.kompody.etnetera.data.database.dao.ResultDao
import com.kompody.etnetera.data.network.api.ResultApi
import com.kompody.etnetera.data.network.api.SportsListApi
import com.kompody.etnetera.data.repository.ResultsRepositoryImpl
import com.kompody.etnetera.data.repository.SportsListRepositoryImpl
import com.kompody.etnetera.domain.repository.ResultsRepository
import com.kompody.etnetera.domain.repository.SportsListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideResultsRepository(resultApi: ResultApi, resultDao: ResultDao): ResultsRepository =
        ResultsRepositoryImpl(resultApi, resultDao)

    @Singleton
    @Provides
    fun provideSportsListRepository(sportsListApi: SportsListApi): SportsListRepository =
        SportsListRepositoryImpl(sportsListApi)
}