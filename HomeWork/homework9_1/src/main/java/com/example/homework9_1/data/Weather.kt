package com.example.homework9_1.data

data class Weather(
        val coord: Coord,
        val main: Main,
        val name: String,
        val weather: List<WeatherX>,
        val wind: Wind
)