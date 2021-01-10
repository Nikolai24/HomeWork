package com.example.homework8.async

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.homework8.database.DBOperation
import com.example.homework8.Item
import com.example.homework8.Listener
import java.util.concurrent.CompletableFuture
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class DBCompletableFuture: DBInterface {
    private val threadPoolExecutor = ThreadPoolExecutor(3, 6, 1, TimeUnit.MILLISECONDS, LinkedBlockingQueue())
    private val operation: DBOperation = DBOperation()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun saveContact(applicationContext: Context, name: String, contact: String, image: String){
        CompletableFuture.runAsync {
            operation.saveContact(applicationContext, name, contact, image); threadPoolExecutor
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun updateContact(applicationContext: Context, name: String, contact: String, image: String, position: Int){
        CompletableFuture.runAsync {
            operation.updateContact(applicationContext, name, contact, image, position); threadPoolExecutor
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun deleteContact(applicationContext: Context, position: Int){
        CompletableFuture.runAsync {
            operation.deleteContact(applicationContext, position); threadPoolExecutor
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getContactsFromBD(applicationContext: Context, listener: Listener){
        CompletableFuture.runAsync {
            val list = mutableListOf<Item>()
            list.addAll(operation.getContactsFromBD(applicationContext))
            listener.onDataReceived(list);
            threadPoolExecutor
        }
    }

    override fun close(applicationContext: Context){

    }
}