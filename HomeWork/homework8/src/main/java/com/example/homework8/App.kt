package com.example.homework8

import android.app.Application

class App: Application() {
    val  dbHelper: DBHelper = DBHelper(this)
}