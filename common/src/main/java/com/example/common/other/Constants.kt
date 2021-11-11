package com.example.common.other

object Constants {
    var DEGREE = "°"
    private const val CELSIUS = "C"
    private const val FAHRENHEITS  = "F"
    private const val KELVINS = "K"
    private const val MILES_HOUR = "mil/h"
    private const val METER_SEC = "m/sec"
    var UNITS_OF_MEASUREMENT_TEMP = "$DEGREE$CELSIUS"
    var UNITS_OF_MEASUREMENT_WIND_SPEED = METER_SEC

    fun setUnitsOfMeasurement(units: String) {
        when(units) {
            "imperial" -> {
                UNITS_OF_MEASUREMENT_TEMP = "$DEGREE$FAHRENHEITS"
                UNITS_OF_MEASUREMENT_WIND_SPEED = MILES_HOUR
            }
            "standard" -> {
                UNITS_OF_MEASUREMENT_TEMP = KELVINS
                UNITS_OF_MEASUREMENT_WIND_SPEED = METER_SEC
            }
            else -> {
                UNITS_OF_MEASUREMENT_TEMP = "$DEGREE$CELSIUS"
                UNITS_OF_MEASUREMENT_WIND_SPEED = METER_SEC
            }
        }
    }

    const val KEY_UNITS = "units"
    const val UNITS_DEFAULT_VALUE = "metric"
    const val KEY_FORECAST = "current_forecast"
}