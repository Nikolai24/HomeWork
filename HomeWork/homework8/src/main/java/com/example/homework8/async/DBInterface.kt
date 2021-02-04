package com.example.homework8.async

import android.content.Context
import com.example.homework8.Listener

interface DBInterface {

    fun getContactsFromBD(applicationContext: Context, listener: Listener) {

    }

    fun saveContact(applicationContext: Context, name:String, contact:String, image:String){

    }

    fun updateContact(applicationContext: Context, name:String, contact:String, image:String, position:Int){

    }

    fun deleteContact(applicationContext: Context, position:Int){

    }

    fun close(applicationContext: Context){

    }
}