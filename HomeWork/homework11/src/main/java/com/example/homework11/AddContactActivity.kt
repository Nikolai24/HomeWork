package com.example.homework11

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

class AddContactActivity : AppCompatActivity() {
    private var image = "phone"
    private lateinit var editName: EditText
    private lateinit var editContact: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        editName = findViewById(R.id.edit_name)
        editContact = findViewById(R.id.edit_contact)
    }

    fun onRadioButtonClicked(view: View) {
        val checked = (view as RadioButton).isChecked
        when (view.getId()) {
            R.id.phone_number -> if (checked) {
                editContact.hint = "Phone number"
                image = "phone"
            }
            R.id.email -> if (checked) {
                editContact.hint = "Email"
                image = "email"
            }
        }
    }

    fun onCancelClick(v: View?) {
        setResult(RESULT_CANCELED)
        finish()
    }

    fun onButtonClick(v: View?) {
        val name = editName.text.toString()
        val contact = editContact.text.toString()
        val item = Item(name, contact, image)
        sendResult(item)
    }

    private fun sendResult(item: Item) {
        val data = Intent()
        data.putExtra("Item", item)
        setResult(RESULT_OK, data)
        finish()
    }
}