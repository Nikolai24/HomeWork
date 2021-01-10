package com.example.homework8

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.homework8.async.DBCompletableFuture
import com.example.homework8.async.DBHandler
import com.example.homework8.async.DBInterface
import com.example.homework8.async.DBRxJava

class AddContactActivity : AppCompatActivity() {
    private lateinit var editName: TextView
    private lateinit var editContact: TextView
    private lateinit var buttonSave: Button
    private lateinit var buttonCancel: Button
    private var image: String = "phone"
    private lateinit var operation: DBInterface
    private val namePreference = "asyncWorkType"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        editName = findViewById(R.id.edit_name)
        editContact = findViewById(R.id.edit_contact)
        buttonSave = findViewById(R.id.buttonSave)
        operation = getAsyncWork()
        buttonSave.setOnClickListener {
            operation.saveContact(applicationContext, editName.text.toString(), editContact.text.toString(), image)
            finish()
        }
        buttonCancel = findViewById(R.id.buttonCancel)
        buttonCancel.setOnClickListener {
            val toast = Toast.makeText(this, "Список контактов не изменился", Toast.LENGTH_LONG)
            toast.show()
            finish()
        }
    }

    override fun onDestroy() {
        operation = getAsyncWork()
        operation.close(applicationContext)
        super.onDestroy()
    }

    fun onRadioButtonClicked(view: View) {
        val checked = (view as RadioButton).isChecked
        val contact = findViewById<EditText>(R.id.edit_contact)
        when (view.getId()) {
            R.id.phone_number -> if (checked) {
                contact.hint = "Phone number"
                image = "phone"
            }
            R.id.email -> if (checked) {
                contact.hint = "Email"
                image = "email"
            }
        }
    }

    private fun loadAsyncWork() : String {
        val sharedPrefs = getSharedPreferences(namePreference, Context.MODE_PRIVATE)
        return sharedPrefs.getString("ASYNC_WORK", "handler").toString()
    }

    private fun getAsyncWork() : DBInterface {
        return when (loadAsyncWork()) {
            "handler" -> {
                DBHandler()
            }
            "completable_future" -> {
                DBCompletableFuture()
            }
            else -> DBRxJava()
        }
    }

    companion object{
        @JvmStatic
        fun newIntent(context: Context) = Intent(context, AddContactActivity::class.java)
    }
}