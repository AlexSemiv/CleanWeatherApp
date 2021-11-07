package com.example.remote.api

import com.example.remote.models.current.CurrentForecastNetworkModel
import com.example.remote.models.search.SearchForecastNetworkModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    companion object {
        private const val API_KEY = "9db8ad32e88e874c9d211366f8394486"
    }

    @GET("data/2.5/onecall")
    suspend fun getCurrentForecast(
        @Query("lat")
        latitude: Double,
        @Query("lon")
        longitude: Double,
        @Query("appid")
        key: String = API_KEY,
        @Query("exclude")
        exclude: String = "minutely,alerts",
        @Query("units")
        units: String = "metric",
        @Query("lang")
        lang: String = "en"
    ): Response<CurrentForecastNetworkModel>

    @GET("data/2.5/forecast")
    suspend fun searchForecastByQuery(
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