package com.example.remote.mappers

import com.example.common.other.Mapper
import com.example.data.models.search.*
import com.example.remote.models.search.SearchForecastNetworkModel
import javax.inject.Inject

class SearchForecastNetworkDataMapper @Inject constructor() :
    Mapper<SearchForecastNetworkModel, SearchForecastDataModel> {
    override fun from(input: SearchForecastNetworkModel?): SearchForecastDataModel {
        return SearchForecastDataModel(
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
                        description = network.weather?.getOrNull(0)?.description,
                        icon = network.weather?.getOrNull(0)?.icon,
                        id = network.weather?.getOrNull(0)?.id,
                        main = network.weather?.getOrNull(0)?.main
                    ),
                    windSpeed = network.wind?.speed
                )
            }
        )
    }

    override fun to(output: SearchForecastDataModel?): SearchForecastNetworkModel {
        TODO("Not yet implemented")
    }
}