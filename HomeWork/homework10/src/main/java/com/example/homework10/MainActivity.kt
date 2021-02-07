package com.example.homework10

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val namePreference = "storageType"
    private val fileName = "Logger"
    private var fileText = ""
    private val broadcastReceiver by lazy { MyBroadcastReceiver() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_storage) {
            startStorageTypeActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startStorageTypeActivity() {
        val intent = Intent(this@MainActivity, StorageTypeActivity::class.java)
        startActivityForResult(intent, 1)
    }

    fun onClickStart(view: View?) {
        val button = findViewById<Button>(R.id.stop_button)
        button.isEnabled = true
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_POWER_CONNECTED)
        }
        registerReceiver(broadcastReceiver, intentFilter)
        startService(Intent(this@MainActivity, MyService::class.java))
    }

    fun onClickStop(view: View?) {
        val button = findViewById<Button>(R.id.stop_button)
        button.isEnabled = false
        if (loadSwitchState()) {
            val dir = getExternalFilesDir(null)
            if (dir != null) {
                fileText = Logger.readText(fileName, dir)
            }
        }  else {
            fileText = Logger.readText(fileName, filesDir)
        }
        val textView = findViewById<TextView>(R.id.events)
        textView.text = ("")
        textView.text = (fileText)
    }

    private fun loadSwitchState() : Boolean {
        val sharedPrefs = getSharedPreferences(namePreference, Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean("KEY_IS_CHECKED", false)
    }
}