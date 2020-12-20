package com.example.homework5_2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val items: MutableList<Item> = ArrayList<Item>()
    private var adapter: DataAdapter? = null

    private val listener: OnItemClickListener = object : OnItemClickListener {
        override fun onItemClick(item: Item?, position: Int) {
            if (item != null) {
                startEditActivity(item, position)
            }
        }
    }

    private fun startEditActivity(item: Item, position: Int) {
        val intent = Intent(this@MainActivity, EditContactActivity::class.java)
        intent.putExtra("name", item.name)
        intent.putExtra("contact", item.contact)
        intent.putExtra("position", position)
        startActivityForResult(intent, REQUEST_ACCESS_TYPE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setInitialData()
        adapter = DataAdapter(items, items, listener)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
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
                adapter?.getFilter()?.filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun setInitialData() {
        items.add(Item("Aaron Bennet", "+375333333333", R.drawable.phone))
        items.add(Item("Alex Blackwell", "ex@ex.com", R.drawable.email))
        items.add(Item("Alicia Malcolm", "ex@ex.com", R.drawable.email))
        items.add(Item("Amelia Earhart", "+375333333333", R.drawable.phone))
        items.add(Item("Antonio Banderas", "+375333333333", R.drawable.phone))
        items.add(Item("Bailey Richards", "ex@ex.com", R.drawable.email))
        items.add(Item("Bob Cobb", "+375333333333", R.drawable.phone))
        items.add(Item("Brian Eno", "ex@ex.com", R.drawable.email))
        items.add(Item("Brooke Wilson", "ex@ex.com", R.drawable.email))
    }

    fun onClick(view: View?) {
        val intent = Intent(this, AddContactActivity::class.java)
        startActivityForResult(intent, REQUEST_ACCESS_TYPE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_ACCESS_TYPE) {
            if (resultCode == RESULT_OK) {
                val array = data!!.getStringArrayExtra(ACCESS_MESSAGE)
                val name = array!![0]
                val contact = array[1]
                val action = array[2]
                if (action.length == 12) {
                    items.add(Item(name, contact, R.drawable.phone))
                } else if (action.length == 5) {
                    items.add(Item(name, contact, R.drawable.email))
                } else if (action.length == 4) {
                    val position = array[3].toInt()
                    items[position].name = name
                    items[position].contact = contact
                } else {
                    val position = array[3].toInt()
                    items.removeAt(position)
                }
                adapter?.setItems(items)
            } else {
                val toast = Toast.makeText(this, "Список контактов не изменился", Toast.LENGTH_LONG)
                toast.show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        const val ACCESS_MESSAGE = "ACCESS_MESSAGE"
        private const val REQUEST_ACCESS_TYPE = 1
    }
}