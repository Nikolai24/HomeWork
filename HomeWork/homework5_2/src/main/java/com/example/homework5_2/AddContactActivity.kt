package com.example.homework5_2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity


class AddContactActivity : AppCompatActivity() {
    var array = arrayOf("", "", "phone_number")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
    }

    fun onRadioButtonClicked(view: View) {
        val checked = (view as RadioButton).isChecked
        val contact = findViewById<EditText>(R.id.edit_contact)
        when (view.getId()) {
            R.id.phone_number -> if (checked) {
                contact.hint = "Phone number"
                array[2] = "phone_number"
            }
            R.id.email -> if (checked) {
                contact.hint = "Email"
                array[2] = "email"
            }
        }
    }

    fun onCancelClick(v: View?) {
        setResult(RESULT_CANCELED)
        finish()
    }

    fun onButtonClick(v: View?) {
        val editName = findViewById<EditText>(R.id.edit_name)
        array[0] = editName.text.toString()
        val editContact = findViewById<EditText>(R.id.edit_contact)
        array[1] = editContact.text.toString()
        val data = Intent()
        data.putExtra(MainActivity.ACCESS_MESSAGE, array)
        setResult(RESULT_OK, data)
        finish()
    }
}