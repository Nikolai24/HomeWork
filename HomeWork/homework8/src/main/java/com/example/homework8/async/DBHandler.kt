package com.example.homework8.async

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.homework8.database.DBOperation
import com.example.homework8.Item
import com.example.homework8.Listener
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class DBHandler: DBInterface {
    private val threadPoolExecutor = ThreadPoolExecutor(3, 6, 1, TimeUnit.MILLISECONDS, LinkedBlockingQueue())
    private val operation: DBOperation = DBOperation()
    private val handler = Handler(Looper.getMainLooper())

    override fun  saveContact(applicationContext: Context, name: String, contact: String, image: String){
        threadPoolExecutor.submit {
            operation.saveContact(applicationContext, name, contact, image)
        }
    }

    override fun updateContact(applicationContext: Context, name: String, contact: String, image: String, position: Int){
        threadPoolExecutor.submit {
            operation.updateContact(applicationContext, name, contact, image, position)
        }
    }

    override fun deleteContact(applicationContext: Context, position: Int){
        threadPoolExecutor.submit {
            operation.deleteContact(applicationContext, position)
        }
    }

    override fun getContactsFromBD(applicationContext: Context, listener: Listener){
        threadPoolExecutor.submit {
            val list = mutableListOf<Item>()
            list.addAll(operation.getContactsFromBD(applicationContext))
            handler.post { listener.onDataReceived(list)}
        }
    }

    override fun close(applicationContext: Context){
        threadPoolExecutor.shutdown()
    }
}