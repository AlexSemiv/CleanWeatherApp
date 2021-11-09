package com.example.local.mapper

import com.example.common.other.Mapper
import com.example.data.models.current.*
import com.example.local.model.current.CurrentForecastLocalModel
import javax.inject.Inject

class CurrentForecastLocalDataMapper @Inject constructor() : Mapper<CurrentForecastLocalModel, CurrentForecastDataModel> {
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
                    dt = dataDaily.dt,
                    feels_like = dataDaily.feels_like,
                    temp = dataDaily.temp,
                )
            },
            hourly = input?.daily?.map { dataHourly ->
                Hourly(
                    dt = dataHourly.dt,
                    feels_like = dataHourly.feels_like,
                    temp = dataHourly.temp
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
                    dt = dataDaily.dt,
                    feels_like = dataDaily.feels_like,
                    temp = dataDaily.temp,
                )
            },
            hourly = output?.daily?.map { dataHourly ->
                com.example.local.model.current.Hourly(
                    dt = dataHourly.dt,
                    feels_like = dataHourly.feels_like,
                    temp = dataHourly.temp
                )
            },
            timezone = output?.timezone
        )
    }
}