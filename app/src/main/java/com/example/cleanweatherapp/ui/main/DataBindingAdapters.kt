package com.example.cleanweatherapp.ui.main

import androidx.databinding.BindingAdapter
import com.example.common.other.Constants
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textview.MaterialTextView
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("app:subtitleSetLastUpdateTime")
fun subtitleSetLastUpdateTime(view: MaterialToolbar, timeInSeconds: Int?) {
    try {
        val sdf = SimpleDateFormat("h:mm a", Locale("en", "EN"))
        val netDate = Date((timeInSeconds)!!.toLong() * 1000)
        view.subtitle = "last update ${sdf.format(netDate)}"
    } catch (e: Exception){
        view.subtitle = "00:00 PM"
    }
}

@BindingAdapter("app:titleSetTimezone")
fun titleSetTimezone(view: MaterialToolbar, timeZone: String?) {
    try {
        view.title = timeZone?.substringBefore(delimiter = "/")
    } catch (e: Exception){
        view.title = "unknown"
    }
}

@BindingAdapter("app:textSetDescription")
fun textSetDescription(view: MaterialTextView, description: String?) {
    try {
        view.text = "${description?.getOrNull(0)?.uppercaseChar()}${description?.substring(1)}"
    } catch (e: Exception){
        view.text = "unknown"
    }
}

@BindingAdapter("app:textSetWindSpeed")
fun textSetWindSpeed(view: MaterialTextView, speed: Double?) {
    try {
        view.text = "$speed${Constants.UNITS_OF_MEASUREMENT_WIND_SPEED}"
    } catch (e: Exception){
        view.text = "unknown"
    }
}

@BindingAdapter("app:dialogTextSetWindSpeed")
fun dialogTextSetWindSpeed(view: MaterialTextView, speed: Double?) {
    try {
        view.text = "Wind speed: $speed${Constants.UNITS_OF_MEASUREMENT_WIND_SPEED}"
    } catch (e: Exception){
        view.text = "unknown"
    }
}

@BindingAdapter("app:textSetTemp")
fun textSetTemp(view: MaterialTextView, temp: Double?) {
    try {
        view.text = "${temp?.toInt()}${Constants.UNITS_OF_MEASUREMENT_TEMP}"
    } catch (e: Exception){
        view.text = "0${Constants.DEGREE}"
    }
}

@BindingAdapter("app:dialogTextSetTemp")
fun dialogTextSetTemp(view: MaterialTextView, temp: Double?) {
    try {
        view.text = "Temperature: ${temp?.toInt()}${Constants.UNITS_OF_MEASUREMENT_TEMP}"
    } catch (e: Exception){
        view.text = "Temperature: 0${Constants.DEGREE}"
    }
}

@BindingAdapter("app:textSetTime")
fun textSetTime(view: MaterialTextView, timeInSeconds: Int?) {
    try {
        val sdf = SimpleDateFormat("h:mm a", Locale("en", "EN"))
        val netDate = Date((timeInSeconds)!!.toLong() * 1000)
        view.text = sdf.format(netDate)
    } catch (e: Exception){
        view.text = "00:00 PM"
    }
}

@BindingAdapter("app:textSetDate")
fun textSetDate(view: MaterialTextView, timeInSeconds: Int?) {
    try {
        val sdf = SimpleDateFormat("EEEE, d MMM", Locale("en", "EN"))
        val netDate = Date((timeInSeconds)!!.toLong() * 1000)
        view.text = sdf.format(netDate)
    } catch (e: Exception){
        view.text = "00:00 PM"
    }
}