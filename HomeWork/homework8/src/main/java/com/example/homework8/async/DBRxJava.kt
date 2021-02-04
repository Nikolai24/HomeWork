package com.example.homework8.async

import android.content.Context
import com.example.homework8.database.DBOperation
import com.example.homework8.Listener
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers

class DBRxJava: DBInterface {
    private val operation: DBOperation = DBOperation()

    override fun  saveContact(applicationContext: Context, name: String, contact: String, image: String){
        Observable.just(operation.saveContact(applicationContext, name, contact, image))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    override fun updateContact(applicationContext: Context, name: String, contact: String, image: String, position: Int){
        Observable.just(operation.updateContact(applicationContext, name, contact, image, position))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    override fun deleteContact(applicationContext: Context, position: Int){
        Observable.just(operation.deleteContact(applicationContext, position))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    override fun getContactsFromBD(applicationContext: Context, listener: Listener){
        Observable.just(operation.getContactsFromBD(applicationContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ listener.onDataReceived(it)}
    }

    override fun close(applicationContext: Context){

    }
}