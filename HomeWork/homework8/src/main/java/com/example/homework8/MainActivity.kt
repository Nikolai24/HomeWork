package com.example.homework8

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework8.async.DBCompletableFuture
import com.example.homework8.async.DBHandler
import com.example.homework8.async.DBInterface
import com.example.homework8.async.DBRxJava
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var items: MutableList<Item> = mutableListOf()
    private lateinit var adapter: DataAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var operation: DBInterface
    private val namePreference = "asyncWorkType"

    private val listener: DataAdapter.OnItemClickListener = object : DataAdapter.OnItemClickListener {
        override fun onItemClick(item: Item, position: Int) {
            startEditActivity(item, position)
        }
    }

    private fun startEditActivity(item: Item, position: Int) {
        val intent = Intent(this@MainActivity, EditContactActivity::class.java)
        intent.putExtra("name", item.name)
        intent.putExtra("contact", item.contact)
        intent.putExtra("image", item.image)
        intent.putExtra("position", position)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = DataAdapter(items, listener)
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        findViewById<FloatingActionButton>(R.id.async).setOnClickListener{
            startActivity(AsyncWorkActivity.newIntent(this))
        }
        findViewById<FloatingActionButton>(R.id.add).setOnClickListener{
            startActivity(AddContactActivity.newIntent(this))
        }
    }

    private val newListener: Listener = object : Listener {
        override fun onDataReceived(list: List<Item>) {
            adapter.setItems(list)
        }
    }

    override fun onResume() {
        super.onResume()
        operation = getAsyncWork()
        operation.getContactsFromBD(applicationContext, newListener)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}