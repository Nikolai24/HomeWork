package com.example.homework7

import android.app.Application

class App: Application() {
    val  dbHelper: DBHelper = DBHelper(this)
}