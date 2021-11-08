package com.example.data.mappers

import com.example.common.other.Mapper
import com.example.data.models.current.CurrentForecastDataModel
import com.example.domain.models.current.*
import javax.inject.Inject

class CurrentForecastDataDomainMapper @Inject constructor(): Mapper<CurrentForecastDataModel, CurrentForecastDomainModel> {
    override fun from(input: CurrentForecastDataModel?): CurrentForecastDomainModel {
        return CurrentForecastDomainModel(
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
                    description = input?.current?.weather?.description,
                    icon = input?.current?.weather?.icon,
                    id = input?.current?.weather?.id,
                    main = input?.current?.weather?.main
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

    override fun to(output: CurrentForecastDomainModel?): CurrentForecastDataModel {
        TODO("Not yet implemented")
    }
}