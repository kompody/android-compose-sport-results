package com.kompody.etnetera.di

import com.kompody.etnetera.ui.base.receivers.CloseApplicationReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ReceiverModule {

    @Provides
    fun provideCloseApplicationReceiver() = CloseApplicationReceiver
}