package com.example.weatherreport.ui.utils

import java.text.SimpleDateFormat
import java.util.*

fun convertEpochToDateFormat(epochTimeInMillis: Long): String {
    val date = Date(epochTimeInMillis)
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(date)
}

fun convertEpochToDayFormat(epochTimeInMillis: Long): String {
    val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
    return sdf.format(Date(epochTimeInMillis * 1000))
}