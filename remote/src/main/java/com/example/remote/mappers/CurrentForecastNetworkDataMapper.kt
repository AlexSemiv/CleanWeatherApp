package com.example.remote.mappers

import com.example.common.base.Mapper
import com.example.data.models.current.*
import com.example.remote.models.current.CurrentForecastNetworkModel
import javax.inject.Inject

class CurrentForecastNetworkDataMapper @Inject constructor():
    Mapper<CurrentForecastNetworkModel, CurrentForecastDataModel> {

    override fun from(input: CurrentForecastNetworkModel?): CurrentForecastDataModel {
        return CurrentForecastDataModel(
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
                weather = Weather(
                    description = input?.current?.weather?.getOrNull(0)?.description,
                    icon = input?.current?.weather?.getOrNull(0)?.icon,
                    id = input?.current?.weather?.getOrNull(0)?.id,
                    main = input?.current?.weather?.getOrNull(0)?.main
                ),
                wind_deg = input?.current?.wind_deg,
                wind_gust = input?.current?.wind_gust,
                wind_speed = input?.current?.wind_speed
            ),
            daily = input?.daily?.map { networkDaily ->
                Daily(
                    clouds = networkDaily.clouds,
                    dew_point = networkDaily.dew_point,
                    dt = networkDaily.dt,
                    feels_like = networkDaily.feels_like?.day,
                    temp = networkDaily.temp?.day,
                    humidity = networkDaily.humidity,
                    pressure = networkDaily.pressure,
                    rain = networkDaily.rain,
                    snow = networkDaily.snow,
                    sunrise = networkDaily.sunrise,
                    sunset = networkDaily.sunset,
                    uvi = networkDaily.uvi,
                    weather = Weather(
                        description = networkDaily.weather?.getOrNull(0)?.description,
                        id = networkDaily.weather?.getOrNull(0)?.id,
                        main = networkDaily.weather?.getOrNull(0)?.main,
                        icon = networkDaily.weather?.getOrNull(0)?.icon
                    ),
                    wind_speed = networkDaily.wind_speed,
                    wind_deg = networkDaily.wind_deg,
                    wind_gust = networkDaily.wind_gust
                )
            },
            timezone = input?.timezone
        )
    }

    override fun to(output: CurrentForecastDataModel?): CurrentForecastNetworkModel {
        TODO("Not yet implemented")
    }
}