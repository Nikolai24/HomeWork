package com.example.homework10

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyBroadcastReceiver : BroadcastReceiver() {

    private val event1: String = "ACTION_SCREEN_OFF"
    private val event2: String = "ACTION_SCREEN_ON"
    private val event3: String = "ACTION_POWER_CONNECTED"

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action ?: "") {
            Intent.ACTION_SCREEN_OFF -> sendEvent(event1, context)
            Intent.ACTION_SCREEN_ON -> sendEvent(event2, context)
            Intent.ACTION_POWER_CONNECTED -> sendEvent(event3, context)
        }
    }

    private fun sendEvent(event: String, context: Context?) {
        val intent = Intent(context, MyService::class.java)
        intent.putExtra("EVENT", event)
        context?.startService(intent)
    }
}