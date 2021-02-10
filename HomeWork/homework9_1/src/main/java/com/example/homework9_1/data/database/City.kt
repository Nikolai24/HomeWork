package com.example.homework9_1.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cities")
class City(
        @PrimaryKey
        @ColumnInfo(name = "City") val city: String,
        @ColumnInfo(name = "lat") val lat: String,
        @ColumnInfo(name = "lon") val lon: String
)