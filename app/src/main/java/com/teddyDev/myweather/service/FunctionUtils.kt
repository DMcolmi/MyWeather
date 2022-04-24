package com.teddyDev.myweather.service

import com.teddyDev.myweather.api.Main
import java.text.SimpleDateFormat
import java.util.*

fun getHyphenIfDoubleNull(doubleValue:Double?):String{
    return doubleValue?.toString() ?: "-"
}

fun getStringTimestamp(): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
    return formatter.format(Date())
}

fun getTimeFromMilliseconds(milliseconds: Long?):String{
    val stringBuilder = StringBuilder()
    milliseconds?.let {
        val formatter = SimpleDateFormat("dd/MM HH:mm")
        stringBuilder.append(formatter.format(Date(milliseconds*1000)))
    }
    return stringBuilder.toString()
}

fun getHash(name: String, country: String): Int {
    return StringBuilder().append(name).append(country).toString().hashCode()
}
