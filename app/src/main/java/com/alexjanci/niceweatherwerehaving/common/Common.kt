package com.alexjanci.niceweatherwerehaving.common

import androidx.annotation.NonNull
import java.lang.StringBuilder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Common {
    val API_KEY: String = "8319fa681bbac48297d98d62fe05eb4a"
    val API_LINK: String = "http://api.openweathermap.org/data/2.5/weather"

    @NonNull
    fun apiRequest(lat: String, long: String): String {
        val sb: StringBuilder = StringBuilder(API_LINK)
        sb.append(String.format("?lat=%s&lon=%s&APPID=%s", lat, long,API_KEY))
        return sb.toString()
    }

    fun unixTimeStampToDateTime(unixTimeStamp: Double): String {
        val dateFormat: DateFormat = SimpleDateFormat("HH:mm")
        val date: Date = Date()
        date.time = unixTimeStamp.toLong() * 1000
        return dateFormat.format(date)
    }

    fun getImage(icon: String): String{
        return String.format("http://openweathermap.org/img/w/%s.ping", icon)
    }

    fun getDateNow(): String{
        val dateFormat: DateFormat = SimpleDateFormat("dd MMMM yyyy HH:mm")
        val date: Date = Date()
        return dateFormat.format(date)
    }
}