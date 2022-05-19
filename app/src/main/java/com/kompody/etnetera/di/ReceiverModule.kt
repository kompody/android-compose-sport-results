package com.kompody.etnetera.di

import com.kompody.etnetera.ui.base.receivers.CloseApplicationReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReceiverModule {

    @Singleton
    @Provides
    fun provideCloseApplicationReceiver() = CloseApplicationReceiver
}