package com.example.homework6

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EditFileActivity : AppCompatActivity() {
    private var fileName: String? = ""
    private var fileText: String = ""
    private var position = 0
    private var storageType: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_file)
        val fileNameView = findViewById<EditText>(R.id.edit_name)
        val fileTextView = findViewById<EditText>(R.id.edit_text)
        val textView = findViewById<TextView>(R.id.text)
        if (intent.hasExtra("name") && intent.hasExtra("position") && intent.hasExtra("storageType")) {
            fileName = intent.getStringExtra("name")
            storageType = intent.getStringExtra("storageType")
            if (storageType == "external") {
                val dir = getExternalFilesDir(null)
                if (dir != null) {
                    fileText = Item.readText(fileName.toString(), dir)
                }
            }  else {
                fileText = Item.readText(fileName.toString(), filesDir)
            }
            position = intent.getIntExtra("position", 0)
        }
        fileNameView.setText(fileName)
        fileTextView.setText(fileText)
        textView.text = (position + 1).toString()
    }

    var array = arrayOf("name", "operation", "position", "text")
    fun onButtonClick(view: View?) {
        val fileNameView = findViewById<EditText>(R.id.edit_name)
        val fileTextView = findViewById<EditText>(R.id.edit_text)
        val editName = fileNameView.text.toString()
        val editText = fileTextView.text.toString()
        array[0] = editName
        array[1] = "edit"
        array[2] = position.toString()
        array[3] = editText
        val data = Intent()
        data.putExtra(MainActivity.ACCESS_MESSAGE, array)
        setResult(RESULT_OK, data)
        finish()
    }

    fun delete(view: View?) {
        if (storageType == "external") {
            val dir = getExternalFilesDir(null)
            if (dir != null) {
                Item.removeFile(fileName.toString(), dir)
            }
        }  else {
            Item.removeFile(fileName.toString(), filesDir)
        }
        array[0] = ""
        array[1] = "delete"
        array[2] = position.toString()
        array[3] = ""
        val data = Intent()
        data.putExtra(MainActivity.ACCESS_MESSAGE, array)
        setResult(RESULT_OK, data)
        finish()
    }
}