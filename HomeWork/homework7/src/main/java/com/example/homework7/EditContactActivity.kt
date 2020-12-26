package com.example.homework7

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EditContactActivity : AppCompatActivity() {
    private lateinit var editName: TextView
    private lateinit var editContact: TextView
    private lateinit var image: String
    var position = 0
    private lateinit var buttonEdit: Button
    private lateinit var buttonDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)
        editName = findViewById(R.id.edit_name)
        editContact = findViewById(R.id.edit_contact)
        val textView = findViewById<TextView>(R.id.text)
        if (intent.hasExtra("name") && intent.hasExtra("contact") && intent.hasExtra("image") && intent.hasExtra("position")) {
            image = intent.getStringExtra("image").toString()
            position = intent.getIntExtra("position", 0)
            editName.text = intent.getStringExtra("name").toString()
            editContact.text = intent.getStringExtra("contact").toString()
            textView.text = (position + 1).toString()
        }
        buttonEdit = findViewById(R.id.buttonEdit)
        buttonEdit.setOnClickListener {
            updateContact(editName.text.toString(), editContact.text.toString())
            finish()
        }
        buttonDelete = findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            deleteContact()
            finish()
        }
    }

    private fun updateContact(textName:String, textContact:String){
        val contentValues = ContentValues().apply {
            put("name", textName)
            put("contact", textContact)
            put("image", image)
        }
        (applicationContext as App)
                .dbHelper
                .readableDatabase
                .update("contacts", contentValues, "" + getContactsFromBD(applicationContext)[position].id + " =id", null)
    }

    private fun deleteContact(){
        (applicationContext as App)
                .dbHelper
                .readableDatabase
                .delete("contacts", "" + getContactsFromBD(applicationContext)[position].id + " =id", null)
    }

    private fun getContactsFromBD(applicationContext: Context) : List<Item> {
        val coursore = (applicationContext as App)
                .dbHelper
                .readableDatabase
                .query("contacts", null, null, null, null, null, null)
        if(coursore != null) {
            val idIndex = coursore.getColumnIndex("id")
            val nameIndex = coursore.getColumnIndex("name")
            val contactIndex = coursore.getColumnIndex("contact")
            val imageIndex = coursore.getColumnIndex("image")
            val list = mutableListOf<Item>()
            while (coursore.moveToNext()) {
                list.add(Item(coursore.getInt(idIndex), coursore.getString(nameIndex), coursore.getString(contactIndex), coursore.getString(imageIndex)))
            }
            coursore.close()
            return list
        }
        return emptyList()
    }
}