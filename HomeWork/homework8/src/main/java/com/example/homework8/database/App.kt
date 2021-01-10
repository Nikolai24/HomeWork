package com.example.homework8.database

import android.app.Application
import com.example.homework8.database.DBHelper

class App: Application() {
    val  dbHelper: DBHelper = DBHelper(this)
}