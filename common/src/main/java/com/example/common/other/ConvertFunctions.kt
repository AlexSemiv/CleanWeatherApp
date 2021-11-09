package com.example.common.other

import com.example.common.R
import java.text.SimpleDateFormat
import java.util.*

object ConvertFunctions {
    fun formattedTitle(timezone: String) : String {
        return try {
            timezone.substringBefore(delimiter = "/")
        } catch (e: Exception) {
            "Weather"
        }
    }

    fun formattedTime(dt: Int): String {
        val sdf = SimpleDateFormat("h:mm a", Locale("en", "EN"))
        return sdf.format(Date((dt).toLong() * 1000))
    }

    fun formattedCurrentDate(dt: Int) : String {
        val sdf = SimpleDateFormat("EEEE, d MMM", Locale("en", "EN"))
        return sdf.format(Date((dt).toLong() * 1000))
    }

    fun formattedDescription(description: String): String {
        return try {
            "${description[0].uppercaseChar()}${description.substring(1)}."
        } catch (e: Exception) {
            "Description"
        }
    }
}