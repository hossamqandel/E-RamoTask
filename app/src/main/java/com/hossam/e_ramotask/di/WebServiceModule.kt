package com.hossam.e_ramotask.di

import com.hossam.e_ramotask.core.data.remote.EndPoints
import com.hossam.e_ramotask.core.data.remote.WebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object WebServiceModule {


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(EndPoints.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Provides
    @Singleton
    fun provideWebService(retrofit: Retrofit) = retrofit.create(WebService::class.java)


}