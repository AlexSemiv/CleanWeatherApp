package com.example.local.mapper

import com.example.common.base.Mapper
import com.example.data.models.current.*
import com.example.local.model.current.CurrentForecastLocalModel
import javax.inject.Inject

class CurrentForecastLocalDataMapper @Inject constructor() :
    Mapper<CurrentForecastLocalModel, CurrentForecastDataModel> {
    override fun from(input: CurrentForecastLocalModel?): CurrentForecastDataModel {
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
                    description = input?.current?.weatherCurrent?.description,
                    icon = input?.current?.weatherCurrent?.icon,
                    id = input?.current?.weatherCurrent?.id,
                    main = input?.current?.weatherCurrent?.main
                ),
                wind_deg = input?.current?.wind_deg,
                wind_gust = input?.current?.wind_gust,
                wind_speed = input?.current?.wind_speed
            ),
            daily = input?.daily?.map { dataDaily ->
                Daily(
                    clouds = dataDaily.clouds,
                    dew_point = dataDaily.dew_point,
                    dt = dataDaily.dt,
                    feels_like = dataDaily.feels_like,
                    temp = dataDaily.temp,
                    humidity = dataDaily.humidity,
                    pressure = dataDaily.pressure,
                    rain = dataDaily.rain,
                    snow = dataDaily.snow,
                    sunrise = dataDaily.sunrise,
                    sunset = dataDaily.sunset,
                    uvi = dataDaily.uvi,
                    weather = Weather(
                        description = dataDaily.weather?.description,
                        id = dataDaily.weather?.id,
                        main = dataDaily.weather?.main,
                        icon = dataDaily.weather?.icon
                    ),
                    wind_speed = dataDaily.wind_speed,
                    wind_deg = dataDaily.wind_deg,
                    wind_gust = dataDaily.wind_gust
                )
            },
            timezone = input?.timezone
        )
    }

    override fun to(output: CurrentForecastDataModel?): CurrentForecastLocalModel {
        return CurrentForecastLocalModel(
            current = com.example.local.model.current.Current(
                clouds = output?.current?.clouds,
                dew_point = output?.current?.dew_point,
                dt = output?.current?.dt,
                feels_like = output?.current?.feels_like,
                humidity = output?.current?.humidity,
                pressure = output?.current?.pressure,
                sunrise = output?.current?.sunrise,
                sunset = output?.current?.sunset,
                temp = output?.current?.temp,
                uvi = output?.current?.uvi,
                visibility = output?.current?.visibility,
                weatherCurrent = com.example.local.model.current.Weather(
                    description = output?.current?.weather?.description,
                    icon = output?.current?.weather?.icon,
                    id = output?.current?.weather?.id,
                    main = output?.current?.weather?.main
                ),
                wind_deg = output?.current?.wind_deg,
                wind_gust = output?.current?.wind_gust,
                wind_speed = output?.current?.wind_speed
            ),
            daily = output?.daily?.map { dataDaily ->
                com.example.local.model.current.Daily(
                    clouds = dataDaily.clouds,
                    dew_point = dataDaily.dew_point,
                    dt = dataDaily.dt,
                    feels_like = dataDaily.feels_like,
                    temp = dataDaily.temp,
                    humidity = dataDaily.humidity,
                    pressure = dataDaily.pressure,
                    rain = dataDaily.rain,
                    snow = dataDaily.snow,
                    sunrise = dataDaily.sunrise,
                    sunset = dataDaily.sunset,
                    uvi = dataDaily.uvi,
                    weather = com.example.local.model.current.Weather(
                        description = dataDaily.weather?.description,
                        id = dataDaily.weather?.id,
                        main = dataDaily.weather?.main,
                        icon = dataDaily.weather?.icon
                    ),
                    wind_speed = dataDaily.wind_speed,
                    wind_deg = dataDaily.wind_deg,
                    wind_gust = dataDaily.wind_gust
                )
            },
            timezone = output?.timezone
        )
    }
}