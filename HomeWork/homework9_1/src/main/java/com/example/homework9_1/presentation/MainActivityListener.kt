package com.example.homework9_1.presentation

import com.example.homework9_1.model.HourlyWeatherModel
import com.example.homework9_1.model.WeatherModel

interface MainActivityListener {
    fun getHourlyWeather(hourlyWeatherModel: List<HourlyWeatherModel>)
    fun getMainWeather(weatherModel: WeatherModel)
    fun onError()
}