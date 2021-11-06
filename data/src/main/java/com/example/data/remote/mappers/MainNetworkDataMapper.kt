package com.example.data.remote.mappers

import com.example.common.other.Mapper
import com.example.data.data_models.current.*
import com.example.data.remote.network_models.current.MainForecastNetworkModel

class MainNetworkDataMapper: Mapper<MainForecastNetworkModel, MainForecastDataModel> {

    override fun from(input: MainForecastNetworkModel?): MainForecastDataModel {
        return MainForecastDataModel(
            current = Current(
                clouds = input?.current?.clouds,
                dew_point = input?.current?.dew_point,
                dt = input?.current?.dt,
                feels_like = input?.current?.feels_like,
                humidity = input?.current?.humidity,
                pressure = input?.current?.pressure,
                sunrise = input?.current?.sunrise,
                sunset = input?.current?.sunset,
                temp = input?.current?.temp,
                uvi = input?.current?.uvi,
                visibility = input?.current?.visibility,
                weatherCurrent = Weather(
                    description = input?.current?.weatherCurrent?.getOrNull(0)?.description,
                    icon = input?.current?.weatherCurrent?.getOrNull(0)?.icon,
                    id = input?.current?.weatherCurrent?.getOrNull(0)?.id,
                    main = input?.current?.weatherCurrent?.getOrNull(0)?.main
                ),
                wind_deg = input?.current?.wind_deg,
                wind_gust = input?.current?.wind_gust,
                wind_speed = input?.current?.wind_speed
            ),
            daily = input?.daily?.map { networkDaily ->
                Daily(
                    dt = networkDaily.dt,
                    feels_like = networkDaily.feels_like?.day,
                    temp = networkDaily.temp?.day)
            },
            hourly = input?.hourly?.map { networkHourly ->
                Hourly(
                    dt = networkHourly.dt,
                    feels_like = networkHourly.feels_like,
                    temp = networkHourly.temp
                )
            },
            timezone = input?.timezone
        )
    }

    override fun to(output: MainForecastDataModel?): MainForecastNetworkModel {
        TODO("Not yet implemented")
    }
}