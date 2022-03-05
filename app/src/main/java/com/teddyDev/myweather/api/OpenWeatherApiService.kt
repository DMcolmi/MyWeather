package com.teddyDev.myweather.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org"
private const val LIMIT = "5"
private const val APPID = ""

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface OpenWeatherApiService {

    @GET("geo/1.0/direct")
    suspend fun getLocation(
        @Query("q") cityName: String,
        @Query("limit") limit: String = LIMIT,
        @Query("appid") api_key: String = APPID
    ): List<LocationData>

    object OpenWeatherApi {
        val openWeatherApiService: OpenWeatherApiService by lazy {
            retrofit.create(OpenWeatherApiService::class.java)
        }
    }

}