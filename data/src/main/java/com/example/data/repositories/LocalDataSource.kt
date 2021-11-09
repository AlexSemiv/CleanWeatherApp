package com.example.data.repositories

import com.example.data.models.current.CurrentForecastDataModel

interface LocalDataSource {

    fun saveCurrentForecastData(forecast: CurrentForecastDataModel)

    fun getLastSavedCurrentForecastData(): CurrentForecastDataModel

    fun getUnits(): String
}