package com.example.local.source

import android.content.SharedPreferences
import com.example.common.other.Constants
import com.example.common.base.Mapper
import com.example.data.models.current.CurrentForecastDataModel
import com.example.data.repositories.LocalDataSource
import com.example.local.model.current.Current
import com.example.local.model.current.CurrentForecastLocalModel
import com.example.local.model.current.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class LocalDataSourceImp @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val mapper: Mapper<CurrentForecastLocalModel, CurrentForecastDataModel>
): LocalDataSource {

    private val defaultEmptyCurrentForecast = CurrentForecastLocalModel(
        current = Current(
            clouds = 0,
            dew_point = 0.0,
            dt = 0,
            feels_like = 0.0,
            humidity = 0,
            pressure = 0,
            sunrise = 0,
            sunset = 0,
            temp = 0.0,
            uvi = 0.0,
            visibility = 0,
            weatherCurrent = Weather(
                description = "Description.",
                icon = "01n",
                id = 0,
                main = "Unknown."
            ),
            wind_deg = 0,
            wind_gust = 0.0,
            wind_speed = 0.0
        ),
        daily = listOf(),
        timezone = "Unknown."
    )

    private val type = object : TypeToken<CurrentForecastLocalModel>() {}.type

    private val defaultEmptyForecastJson = Gson().toJson(defaultEmptyCurrentForecast, type)

    override fun saveCurrentForecastData(forecast: CurrentForecastDataModel) {
        val json = Gson().toJson(mapper.to(forecast), type)
        sharedPreferences.edit()
            .putString(Constants.KEY_FORECAST, json)
            .apply()
    }

    override fun getLastSavedCurrentForecastData(): CurrentForecastDataModel {
        val json = sharedPreferences.getString(Constants.KEY_FORECAST, defaultEmptyForecastJson)
        return mapper.from(Gson().fromJson(json, type) as CurrentForecastLocalModel)
    }

    override fun getUnits(): String {
        return sharedPreferences.getString(Constants.KEY_UNITS, Constants.UNITS_DEFAULT_VALUE) ?: "metric"
    }
}