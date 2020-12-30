package com.example.homework8

import android.content.ContentValues
import android.content.Context

class DBOperation {
    fun getContactsFromBD(applicationContext: Context) : List<Item> {
        val list = mutableListOf<Item>()
        val coursore = (applicationContext as App)
                .dbHelper
                .readableDatabase
                .query("contacts", null, null, null, null, null, null)
        if(coursore != null) {
            val idIndex = coursore.getColumnIndex("id")
            val nameIndex = coursore.getColumnIndex("name")
            val contactIndex = coursore.getColumnIndex("contact")
            val imageIndex = coursore.getColumnIndex("image")
            while (coursore.moveToNext()) {
                list.add(Item(coursore.getInt(idIndex), coursore.getString(nameIndex), coursore.getString(contactIndex), coursore.getString(imageIndex)))
            }
            coursore.close()
            return list
        }
        return emptyList()
    }

    fun  saveContact(applicationContext: Context, name:String, contact:String, image:String){
        Thread(Runnable {
            val contentValues = ContentValues().apply {
                put("name", name)
                put("contact", contact)
                put("image", image)
            }
            (applicationContext as App)
                    .dbHelper
                    .readableDatabase
                    .insert("contacts", null, contentValues)
        }).start()
    }

    fun updateContact(applicationContext: Context, name:String, contact:String, image:String, position:Int){
        Thread(Runnable {
            val contentValues = ContentValues().apply {
                put("name", name)
                put("contact", contact)
                put("image", image)
            }
            (applicationContext as App)
                    .dbHelper
                    .readableDatabase
                    .update("contacts", contentValues, "" + getContactsFromBD(applicationContext)[position].id + " =id", null)
        }).start()
    }

    fun deleteContact(applicationContext: Context, position:Int){
        Thread(Runnable {
            (applicationContext as App)
                    .dbHelper
                    .readableDatabase
                    .delete("contacts", "" + getContactsFromBD(applicationContext)[position].id + " =id", null)
        }).start()
    }
}