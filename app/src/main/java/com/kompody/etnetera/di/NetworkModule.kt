package com.kompody.etnetera.di

import com.google.gson.Gson
import com.kompody.etnetera.BuildConfig
import com.kompody.etnetera.data.network.HeaderInterceptor
import com.kompody.etnetera.data.network.HttpErrorMappingInterceptor
import com.kompody.etnetera.data.network.MockInterceptor
import com.kompody.etnetera.utils.buildOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ) = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: HeaderInterceptor,
        httpErrorMappingInterceptor: HttpErrorMappingInterceptor,
        mockInterceptor: MockInterceptor
    ) = buildOkHttpClient {
//        addInterceptor(headerInterceptor)
        addInterceptor(httpErrorMappingInterceptor)
        addInterceptor(mockInterceptor)
    }
}