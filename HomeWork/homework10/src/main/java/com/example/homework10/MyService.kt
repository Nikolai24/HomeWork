package com.example.homework10

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.CompletableFuture
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class MyService : Service() {
    private val namePreference = "storageType"
    private val fileName = "Logger"
    private val threadPoolExecutor = ThreadPoolExecutor(3, 6, 1, TimeUnit.MILLISECONDS, LinkedBlockingQueue())

    override fun onCreate() {
        super.onCreate()
        var directory = filesDir
        if (loadSwitchState()) {
            directory = getExternalFilesDir(null)
        }
        Logger.create(fileName, directory)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val bl: Bundle? = intent?.extras
        val event: String? = bl?.getString("EVENT")
        logger(event)
        return START_NOT_STICKY
    }

    private fun logger(event: String?) {
        CompletableFuture.runAsync {
            var date = Date(System.currentTimeMillis())
            var formatter = SimpleDateFormat("yyyy/MM/dd HH:mm")
            val currentDate: String = formatter.format(date)
            if (event != null) {
                var directory = filesDir
                if (loadSwitchState()) {
                    directory = getExternalFilesDir(null)
                }
                Logger.writeText(fileName, "$currentDate - $event\n", directory)
            }; threadPoolExecutor
        }
    }

    private fun loadSwitchState() : Boolean {
        val sharedPrefs = getSharedPreferences(namePreference, Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean("KEY_IS_CHECKED", false)
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}