package com.example.homework8

import android.content.Context

interface DBInterface {

    fun getContactsFromBD(applicationContext: Context) : List<Item> {
        return emptyList()
    }

    fun saveContact(applicationContext: Context, name:String, contact:String, image:String){

    }

    fun updateContact(applicationContext: Context, name:String, contact:String, image:String, position:Int){

    }

    fun deleteContact(applicationContext: Context, position:Int){

    }
}