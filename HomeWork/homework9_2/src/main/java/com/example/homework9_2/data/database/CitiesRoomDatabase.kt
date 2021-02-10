package com.example.homework9_2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [City::class], version = 1, exportSchema = false)
abstract class CitiesRoomDatabase : RoomDatabase() {
    abstract fun getCityDao(): CityDao
    companion object {
        @Volatile
        private var INSTANCE: CitiesRoomDatabase? = null
        @Synchronized
        fun getDatabase(context: Context): CitiesRoomDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CitiesRoomDatabase::class.java,
                        "cities_database"
                ).build()
            }
            return INSTANCE as CitiesRoomDatabase
        }
    }
}