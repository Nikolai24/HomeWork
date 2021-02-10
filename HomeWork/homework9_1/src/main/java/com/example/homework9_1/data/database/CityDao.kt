package com.example.homework9_1.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface CityDao {
    @Query("SELECT * FROM Cities")
    fun getCities(): Flowable<List<City>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: City)
}