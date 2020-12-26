package com.example.homework7

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var items: MutableList<Item> = mutableListOf()
    private lateinit var adapter: DataAdapter
    private lateinit var recyclerView: RecyclerView

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
        findViewById<FloatingActionButton>(R.id.add).setOnClickListener{
            startActivity(AddContactActivity.newIntent(this))
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.setItems(getContactsFromBD())
    }

    private fun getContactsFromBD() : List<Item> {
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