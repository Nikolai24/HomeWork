package com.example.homework9_2.data.api

import com.example.homework9_2.data.HourlyWeather
import com.example.homework9_2.data.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    fun getWeather(
            @Query("q") id: String,
            @Query("appid") api_key: String = "df91bcc9e878836e719b2c7d8f6e4e18",
            @Query("units") degree: String
    ): Call<Weather>

    @GET("onecall")
    fun getHourlyWeather(
            @Query("lat") lat: String,
            @Query("lon") lon: String,
            @Query("units") degree: String,
            @Query("exclude") exclude: String = "alerts",
            @Query("appid") api_key: String = "df91bcc9e878836e719b2c7d8f6e4e18"
    ): Call<HourlyWeather>
}