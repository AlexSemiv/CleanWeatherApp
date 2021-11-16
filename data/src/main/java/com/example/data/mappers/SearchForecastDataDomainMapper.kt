package com.example.data.mappers

import com.example.common.other.Mapper
import com.example.data.models.search.*
import com.example.domain.models.search.City
import com.example.domain.models.search.Main
import com.example.domain.models.search.SearchForecastDomainModel
import com.example.domain.models.search.ThreeHours
import com.example.domain.models.search.Weather
import javax.inject.Inject

class SearchForecastDataDomainMapper @Inject constructor() : Mapper<SearchForecastDataModel, SearchForecastDomainModel> {

    override fun from(input: SearchForecastDataModel?): SearchForecastDomainModel {
        return SearchForecastDomainModel(
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

    override fun to(output: SearchForecastDomainModel?): SearchForecastDataModel {
        TODO("Not yet implemented")
    }
}