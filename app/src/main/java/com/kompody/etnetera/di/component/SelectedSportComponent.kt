package com.kompody.etnetera.di.component

import androidx.lifecycle.SavedStateHandle
import dagger.BindsInstance
import dagger.hilt.DefineComponent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Scope
import javax.inject.Singleton

@Scope
@Retention
annotation class SelectedSportScope

@DefineComponent(parent = SingletonComponent::class)
@SelectedSportScope
interface SelectedSportComponent

@DefineComponent.Builder
interface SelectedSportComponentBuilder {
    fun savedStateHandle(@BindsInstance savedStateHandle: SavedStateHandle): SelectedSportComponentBuilder
    fun build(): SelectedSportComponent
}

@EntryPoint
@InstallIn(SelectedSportComponent::class)
interface SelectedSportEntryPoint {
    @Singleton
    fun getSavedStateHandle(): SavedStateHandle
}
