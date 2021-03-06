package com.kompody.etnetera.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kompody.etnetera.utils.ResourceDelegate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .serializeNulls()
        .create()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        val name = context.packageName + "_prefs"
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideResourceDelegate(@ApplicationContext context: Context): ResourceDelegate =
        ResourceDelegate(context)
}