package com.example.homework8

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.homework8.async.DBCompletableFuture
import com.example.homework8.async.DBHandler
import com.example.homework8.async.DBInterface
import com.example.homework8.async.DBRxJava

class EditContactActivity : AppCompatActivity() {
    private lateinit var editName: TextView
    private lateinit var editContact: TextView
    private lateinit var image: String
    private var position = 0
    private lateinit var buttonEdit: Button
    private lateinit var buttonDelete: Button
    private lateinit var operation: DBInterface
    private val namePreference = "asyncWorkType"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)
        editName = findViewById(R.id.edit_name)
        editContact = findViewById(R.id.edit_contact)
        val textView = findViewById<TextView>(R.id.text)
        operation = getAsyncWork()
        if (intent.hasExtra("name") && intent.hasExtra("contact") && intent.hasExtra("image") && intent.hasExtra("position")) {
            image = intent.getStringExtra("image").toString()
            position = intent.getIntExtra("position", 0)
            editName.text = intent.getStringExtra("name").toString()
            editContact.text = intent.getStringExtra("contact").toString()
            textView.text = (position + 1).toString()
        }
        buttonEdit = findViewById(R.id.buttonEdit)
        buttonEdit.setOnClickListener {
            operation.updateContact(applicationContext, editName.text.toString(), editContact.text.toString(), image, position)
            finish()
        }
        buttonDelete = findViewById(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            operation.deleteContact(applicationContext, position)
            finish()
        }
    }

    override fun onDestroy() {
        operation = getAsyncWork()
        operation.close(applicationContext)
        super.onDestroy()
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
        fun newIntent(context: Context) = Intent(context, EditContactActivity::class.java)
    }
}