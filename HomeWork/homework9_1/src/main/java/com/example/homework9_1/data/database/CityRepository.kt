package com.example.homework9_1.data.database

import com.example.homework9_1.data.Weather
import io.reactivex.Flowable

interface CityRepository {
    fun getCitiesFromDb(): Flowable<List<City>>
    fun insertCityInDb(weather: Weather)
}