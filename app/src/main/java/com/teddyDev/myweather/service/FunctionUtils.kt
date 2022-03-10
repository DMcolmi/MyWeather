package com.teddyDev.myweather.service

fun getHyphenIfDoubleNull(doubleValue:Double?):String{
    return doubleValue?.toString() ?: "-"
}