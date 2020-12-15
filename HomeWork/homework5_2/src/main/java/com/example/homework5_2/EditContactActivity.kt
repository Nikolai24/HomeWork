package com.example.homework5_2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class EditContactActivity : AppCompatActivity() {
    var name: String? = "Name not set"
    var contact: String? = "Contact not set"
    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)
        val nameView = findViewById<EditText>(R.id.edit_name)
        val contactView = findViewById<EditText>(R.id.edit_contact)
        val textView = findViewById<TextView>(R.id.text)
        if (intent.hasExtra("name") && intent.hasExtra("contact") && intent.hasExtra("position")) {
            name = intent.getStringExtra("name")
            contact = intent.getStringExtra("contact")
            position = intent.getIntExtra("position", 0)
        }
        nameView.setText(name)
        contactView.setText(contact)
        textView.text = (position + 1).toString()
    }

    var array = arrayOf("name", "contact", "operation", "position")
    fun onButtonClick(view: View?) {
        val editName = findViewById<EditText>(R.id.edit_name)
        array[0] = editName.text.toString()
        val editContact = findViewById<EditText>(R.id.edit_contact)
        array[1] = editContact.text.toString()
        array[2] = "edit"
        array[3] = position.toString()
        val data = Intent()
        data.putExtra(MainActivity.ACCESS_MESSAGE, array)
        setResult(RESULT_OK, data)
        finish()
    }

    fun delete(view: View?) {
        val editName = findViewById<EditText>(R.id.edit_name)
        array[0] = editName.text.toString()
        val editContact = findViewById<EditText>(R.id.edit_contact)
        array[1] = editContact.text.toString()
        array[2] = "delete"
        array[3] = position.toString()
        val data = Intent()
        data.putExtra(MainActivity.ACCESS_MESSAGE, array)
        setResult(RESULT_OK, data)
        finish()
    }
}