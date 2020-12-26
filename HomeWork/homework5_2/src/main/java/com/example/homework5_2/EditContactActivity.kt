package com.example.homework5_2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EditContactActivity : AppCompatActivity() {
    private var position = 0
    private lateinit var editName: EditText
    private lateinit var editContact: EditText
    private lateinit var item: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)
        editName = findViewById(R.id.edit_name)
        editContact = findViewById(R.id.edit_contact)
        val textView = findViewById<TextView>(R.id.text)
        if (intent.hasExtra("Item") && intent.hasExtra("position")) {
            item = (intent.getParcelableExtra<Item>("Item") as Item)
            position = intent.getIntExtra("position", 0)
            editName.setText(item.name)
            editContact.setText(item.contact)
            textView.text = (position + 1).toString()
        }
    }

    fun onButtonClick(view: View?) {
        item.name =  editName.text.toString()
        item.contact = editContact.text.toString()
        sendResult(item, position)
    }

    fun delete(view: View?) {
        item.image = "delete"
        sendResult(item, position)
    }

    private fun sendResult(item: Item, position: Int) {
        val data = Intent()
        data.putExtra("Item", item)
        data.putExtra("position", position)
        setResult(RESULT_OK, data)
        finish()
    }
}