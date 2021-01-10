package com.example.homework8

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AsyncWorkActivity : AppCompatActivity() {
    private lateinit var buttonSave: Button
    private lateinit var buttonCancel: Button
    private var work: String = "handler"
    private val namePreference = "asyncWorkType"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_work)
        work = loadAsyncWork()
        when (work) {
            "handler" -> {
                val radioButton: RadioButton = findViewById(R.id.handler)
                radioButton.isChecked = true
            }
            "completable_future" -> {
                val radioButton: RadioButton = findViewById(R.id.completable_future)
                radioButton.isChecked = true
            }
            "rxjava" -> {
                val radioButton: RadioButton = findViewById(R.id.rxjava)
                radioButton.isChecked = true
            }
        }
        buttonSave = findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener {
            saveAsyncWork(work)
            finish()
        }
        buttonCancel = findViewById(R.id.buttonCancel)
        buttonCancel.setOnClickListener {
            val toast = Toast.makeText(this, "Тип асинхронной работы не изменился", Toast.LENGTH_LONG)
            toast.show()
            finish()
        }
    }

    fun onRadioButtonClicked(view: View) {
        val checked = (view as RadioButton).isChecked
        when (view.getId()) {
            R.id.handler -> if (checked) {
                work = "handler"
            }
            R.id.completable_future -> if (checked) {
                work = "completable_future"
            }
            R.id.rxjava -> if (checked) {
                work = "rxjava"
            }
        }
    }

    private fun saveAsyncWork(work: String) {
        val sharedPrefs = getSharedPreferences(namePreference, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString("ASYNC_WORK", work)
        editor.apply()
    }

    private fun loadAsyncWork() : String {
        val sharedPrefs = getSharedPreferences(namePreference, Context.MODE_PRIVATE)
        return sharedPrefs.getString("ASYNC_WORK", "handler").toString()
    }

    companion object{
        @JvmStatic
        fun newIntent(context: Context) = Intent(context, AsyncWorkActivity::class.java)
    }
}