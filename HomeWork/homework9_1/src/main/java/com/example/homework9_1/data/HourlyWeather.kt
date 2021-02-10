package com.example.homework9_1.data

data class HourlyWeather(
        val hourly: List<Hourly>,
        val lat: Double,
        val lon: Double
)