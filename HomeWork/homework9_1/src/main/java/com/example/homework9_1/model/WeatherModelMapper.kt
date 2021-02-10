package com.example.homework9_1.model

import com.example.homework9_1.data.Weather
import kotlin.math.roundToInt

class WeatherModelMapper : (Weather) -> WeatherModel {
    override fun invoke(weather: Weather): WeatherModel =
            WeatherModel(
                    cityName = weather.name,
                    temp = "${weather.main.temp.roundToInt()}",
                    description = weather.weather[0].description
            )
}