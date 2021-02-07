package com.example.homework11

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
        intent.putExtra("Item", item)
        intent.putExtra("position", position)
        startActivityForResult(intent, 2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val uri = Uri.parse("content://com.example.homework7/contacts")
        val cursor = contentResolver.query(uri, null, null, null, null)
        if (cursor != null) {
            val name = cursor.getColumnIndex("name")
            val contact = cursor.getColumnIndex("contact")
            val image = cursor.getColumnIndex("image")
            while (cursor.moveToNext()) {
                items.add(Item(cursor.getString(name), cursor.getString(contact), cursor.getString(image)))
            }
        }
        adapter = DataAdapter(items, listener)
        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
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

    fun onClick(view: View?) {
        val intent = Intent(this, AddContactActivity::class.java)
        startActivityForResult(intent, 1)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            items.add(data.getParcelableExtra<Item>("Item") as Item)
            adapter.setItems(items)
        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            val item = data.getParcelableExtra<Item>("Item") as Item
            val position = data.getIntExtra("position", 0)
            if (item.image == "delete") {
                items.removeAt(position)
            } else {
                items[position] = item
            }
            adapter.setItems(items)
        } else {
            val toast = Toast.makeText(this, "Список контактов не изменился", Toast.LENGTH_LONG)
            toast.show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("items", items as ArrayList<out Parcelable>)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        items = savedInstanceState.getParcelableArrayList("items")!!
        adapter = DataAdapter(items, listener)
        recyclerView.adapter = adapter
    }
}