package com.example.homework7

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddContactActivity : AppCompatActivity() {
    private lateinit var editName: TextView
    private lateinit var editContact: TextView
    private lateinit var buttonSave: Button
    private lateinit var buttonCancel: Button
    private var image: String = "phone"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        editName = findViewById(R.id.edit_name)
        editContact = findViewById(R.id.edit_contact)
        buttonSave = findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener {
            saveContact(editName.text.toString(), editContact.text.toString())
            finish()
        }
        buttonCancel = findViewById(R.id.buttonCancel)
        buttonCancel.setOnClickListener {
            val toast = Toast.makeText(this, "Список контактов не изменился", Toast.LENGTH_LONG)
            toast.show()
            finish()
        }
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

    private fun saveContact(textName:String, textContact:String){
        val contentValues = ContentValues().apply {
            put("name", textName)
            put("contact", textContact)
            put("image", image)
        }
        (applicationContext as App)
                .dbHelper
                .readableDatabase
                .insert("contacts", null, contentValues)
    }
    companion object{
        @JvmStatic
        fun newIntent(context: Context) = Intent(context, AddContactActivity::class.java)
    }
}