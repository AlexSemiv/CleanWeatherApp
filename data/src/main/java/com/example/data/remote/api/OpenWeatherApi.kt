package com.example.data.remote.api

import com.example.data.remote.models.current.MainForecastNetworkModel
import com.example.data.remote.models.search.SearchForecastNetworkModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface OpenWeatherApi {

    companion object {
        private const val API_KEY = "9db8ad32e88e874c9d211366f8394486"
    }

    @GET("data/2.5/onecall")
    suspend fun getForecast(
        @Query("lat")
        latitude: Double,
        @Query("lon")
        longitude: Double,
        @Query("appid")
        key: String = API_KEY,
        @Query("exclude")
        exclude: String = "minutely",
        @Query("units")
        units: String = "metric",
        @Query("lang")
        lang: String = "en"
    ): Response<MainForecastNetworkModel>

    @GET("data/2.5/forecast")
    suspend fun searchForecastByCityName(
        @Query("q")
        query: String,
        @Query("appid")
        key: String = API_KEY,
        @Query("units")
        units: String = "metric",
        @Query("lang")
        lang: String = "en"
    ): Response<SearchForecastNetworkModel>
}