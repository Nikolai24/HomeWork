package com.example.homework9_1.model

import com.example.homework9_1.data.Weather
import com.example.homework9_1.data.database.City

class CityModelMapper: (Weather)->(City) {
    override fun invoke(weather: Weather): City = City(
            city = weather.name,
            lat = weather.coord.lat.toString(),
            lon = weather.coord.lon.toString()
    )
}