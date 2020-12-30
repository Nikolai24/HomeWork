package com.example.homework8

import android.content.Context
import android.os.Handler
import android.os.Looper
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class DBHandler( ): DBInterface {

    private val threadPoolExecutor = ThreadPoolExecutor(3, 6, 1, TimeUnit.MILLISECONDS, LinkedBlockingQueue())
    private val operation: DBOperation = DBOperation()

    private val handler = Handler(Looper.getMainLooper())

//    handler.post{
//
//    }

    override fun  saveContact(applicationContext: Context, name: String, contact: String, image: String){
        threadPoolExecutor.submit {
            operation.saveContact(applicationContext, name, contact, image)
        }
        threadPoolExecutor.shutdown()
    }

    override fun updateContact(applicationContext: Context, name: String, contact: String, image: String, position: Int){
        threadPoolExecutor.submit {
            operation.updateContact(applicationContext, name, contact, image, position)
        }
        threadPoolExecutor.shutdown()
    }

    override fun deleteContact(applicationContext: Context, position: Int){
        threadPoolExecutor.submit {
            operation.deleteContact(applicationContext, position)
        }
        threadPoolExecutor.shutdown()
    }

    override fun getContactsFromBD(applicationContext: Context) : List<Item> {
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

//    override fun getContactsFromBD(applicationContext: Context) : List<Item> {
//        var list = mutableListOf<Item>()
//        threadPoolExecutor.submit {
//            val list = mutableListOf<Item>()
//            list.addAll(operation.getContactsFromBD(applicationContext))
//            handler.post { listener.onDataReceived(list) }
//        }
//        threadPoolExecutor.shutdown()
//        if (list.isEmpty()) {
//            return emptyList()
//        }
//        return list
//    }
}