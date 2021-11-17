package com.example.presentation.mappers

import com.example.common.base.Mapper
import com.example.domain.models.search.*
import com.example.presentation.models.search.City
import com.example.presentation.models.search.Main
import com.example.presentation.models.search.SearchForecastUiModel
import com.example.presentation.models.search.ThreeHours
import com.example.presentation.models.search.Weather
import javax.inject.Inject

class SearchForecastDomainUiModel @Inject constructor() :
    Mapper<SearchForecastDomainModel, SearchForecastUiModel> {
    override fun from(input: SearchForecastDomainModel?): SearchForecastUiModel {
        return SearchForecastUiModel(
            city = City(
                country = input?.city?.country,
                name = input?.city?.name
            ),
            list = input?.list?.map { network ->
                ThreeHours(
                    dt = network.dt,
                    dt_txt = network.dt_txt,
                    Main(
                        feels_like = network.main?.feels_like,
                        humidity = network.main?.humidity,
                        pressure = network.main?.pressure,
                        temp = network.main?.temp
                    ),
                    Weather(
                        description = network.weather?.description,
                        icon = network.weather?.icon,
                        id = network.weather?.id,
                        main = network.weather?.main
                    ),
                    windSpeed = network.windSpeed
                )
            }
        )
    }

    override fun to(output: SearchForecastUiModel?): SearchForecastDomainModel {
        TODO("Not yet implemented")
    }
}