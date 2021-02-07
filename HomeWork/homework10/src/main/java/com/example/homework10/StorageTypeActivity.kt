package com.example.homework10

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat

class StorageTypeActivity : AppCompatActivity() {
    private val namePreference = "storageType"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage_type)
        val switchStorage = findViewById<SwitchCompat>(R.id.switchStorage)
        val isExternalStorage = loadSwitchState()
        switchStorage.isChecked = isExternalStorage
        switchStorage.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) { toast(getString(R.string.select_external))
                saveSwitchState(isChecked)
            }
            else { toast(getString(R.string.select_internal))
                saveSwitchState(isChecked)
            }
        }
    }

    private fun toast (textToast: String) {
        val toast = Toast.makeText(this, textToast, Toast.LENGTH_LONG)
        toast.show()
    }

    private fun saveSwitchState(isChecked: Boolean) {
        val sharedPrefs = getSharedPreferences(namePreference , Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putBoolean("KEY_IS_CHECKED", isChecked)
        editor.apply()
    }

    private fun loadSwitchState() : Boolean {
        val sharedPrefs = getSharedPreferences(namePreference , Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean("KEY_IS_CHECKED", false)
    }

    fun onButtonClick(view: View?) {
        setResult(Activity.RESULT_OK)
        finish()
    }
}