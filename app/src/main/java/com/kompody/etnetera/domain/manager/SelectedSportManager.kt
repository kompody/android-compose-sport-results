package com.kompody.etnetera.domain.manager

import androidx.lifecycle.SavedStateHandle
import com.google.gson.Gson
import com.kompody.etnetera.di.component.SelectedSportComponent
import com.kompody.etnetera.di.component.SelectedSportComponentBuilder
import com.kompody.etnetera.di.component.SelectedSportEntryPoint
import com.kompody.etnetera.domain.entity.SportModel
import com.kompody.etnetera.utils.Const
import com.kompody.etnetera.utils.extensions.fromJson
import dagger.hilt.EntryPoints
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SelectedSportManager @Inject constructor(
    private val componentBuilder: SelectedSportComponentBuilder,
    private val gson: Gson
) {
    private var component: SelectedSportComponent? = null

    fun create(savedStateHandle: SavedStateHandle) {
        component = componentBuilder
            .savedStateHandle(savedStateHandle)
            .build()
    }

    private fun getSavedStateHandle(): SavedStateHandle =
        EntryPoints.get(component, SelectedSportEntryPoint::class.java).getSavedStateHandle()

    fun observeSelectedSport(subscriber: (SportModel) -> Unit){
        getSavedStateHandle().getLiveData<String>(Const.SELECTED_SPORT).observeForever { data ->
            val sportItem: SportModel = gson.fromJson(data)
            subscriber(sportItem)
        }
    }

    fun setSelectedSport(sportItem: SportModel) {
        getSavedStateHandle().set(Const.SELECTED_SPORT, gson.toJson(sportItem))
    }
}