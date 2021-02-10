package com.example.homework9_2.data.database

import com.example.homework9_2.data.Weather
import io.reactivex.Flowable

interface CityRepository {
    fun getCitiesFromDb(): Flowable<List<City>>
    fun insertCityInDb(weather: Weather)
}