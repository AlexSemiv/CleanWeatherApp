package com.example.cleanweatherapp.di.modules

import com.example.remote.api.OpenWeatherApi
import com.example.domain.qualifiers.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @ApplicationScope
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @ApplicationScope
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .retryOnConnectionFailure(true)
            .build()
    }

    @ApplicationScope
    @Provides
    fun provideRetrofitInstance(
        httpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @ApplicationScope
    @Provides
    fun provideApiWeather(
        retrofit: Retrofit
    ): OpenWeatherApi {
        return retrofit.create(OpenWeatherApi::class.java)
    }
}