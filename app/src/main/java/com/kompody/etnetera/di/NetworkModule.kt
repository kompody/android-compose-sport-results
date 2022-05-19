package com.kompody.etnetera.di

import com.google.gson.Gson
import com.kompody.etnetera.BuildConfig
import com.kompody.etnetera.data.network.HeaderInterceptor
import com.kompody.etnetera.data.network.HttpErrorMappingInterceptor
import com.kompody.etnetera.data.network.MockInterceptor
import com.kompody.etnetera.data.network.api.ResultApi
import com.kompody.etnetera.data.network.api.SportsListApi
import com.kompody.etnetera.utils.buildOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideResultApi(retrofit: Retrofit): ResultApi = retrofit.create()

    @Provides
    fun provideSportsListApi(retrofit: Retrofit): SportsListApi = retrofit.create()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ) = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideOkHttpClient(
        headerInterceptor: HeaderInterceptor,
        httpErrorMappingInterceptor: HttpErrorMappingInterceptor,
        mockInterceptor: MockInterceptor
    ) = buildOkHttpClient {
//        addInterceptor(headerInterceptor)
//        addInterceptor(httpErrorMappingInterceptor)
        addInterceptor(mockInterceptor)
    }
}