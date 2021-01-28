package com.example.homework7

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "contacts", null, 1) {
    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL("CREATE TABLE contacts (id INTEGER Primary key autoincrement, name TEXT, contact TEXT, image TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }
}