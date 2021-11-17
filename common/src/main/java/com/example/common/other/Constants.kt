package com.example.common.other

import com.example.common.other.Constants.toFormattedHoursAndMinutes
import java.text.SimpleDateFormat
import java.util.*

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


    fun String.toFormattedTitle() = substringBefore(delimiter = "/")

    fun String.toFormattedDescription() = try {
        getOrNull(0)?.uppercaseChar()?.plus(substring(1))
    } catch (e: Exception) {
        "Description"
    }

    fun Int.toFormattedHoursAndMinutes(): String = try {
        val sdf = SimpleDateFormat("h:mm a", Locale("en", "EN"))
        val netDate = Date((this).toLong() * 1000)
        sdf.format(netDate) ?: "00:00 PM"
    } catch (e: Exception) {
        "00:00 AM"
    }

    fun Int.toFormattedDate(): String = try {
        val sdf = SimpleDateFormat("EEEE, d MMM", Locale("en", "EN"))
        val netDate = Date((this).toLong() * 1000)
        sdf.format(netDate) ?: "Friday, 10 January"
    } catch (e: Exception) {
        "Friday, 10 January"
    }

    fun Int.toFormattedFullTime(): String = try {
        val sdf = SimpleDateFormat("d MMM | h:mm", Locale("en", "EN"))
        val netDate = Date((this).toLong() * 1000)
        sdf.format(netDate) ?: "1970-12-10"
    } catch (e: Exception) {
        "1970-12-10"
    }

    const val LOCATION_REQUEST_INTERVAL = 30 * 1000L
    const val FASTEST_LOCATION_INTERVAL = 10 * 1000L

    const val TIMEOUT_SEARCHING = 1000L
}