package com.example.driverapp

import android.content.Context
import com.example.driverapp.Repository.RepositoryImpl
import com.example.driverapp.Retrofit.RetrofitApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Singleton
    @Provides
    fun provideLoginRepository(
        api: RetrofitApi
    ) = RepositoryImpl(api)


    @Provides
    @Singleton
    fun provideApi(): RetrofitApi {
        return Retrofit.Builder()
            .baseUrl(Core.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitApi::class.java)
    }


}