package com.example.homework6

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val items: MutableList<Item> = ArrayList<Item>()
    private var files = mutableListOf<String>()
    private var adapter: DataAdapter? = null
    private val namePreference = "storageType"

    private val listener: OnItemClickListener = object : OnItemClickListener {
        override fun onItemClick(item: Item?, position: Int) {
            if (item != null) {
                startEditActivity(item, position)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = DataAdapter(items, listener)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_storage) {
            startStorageTypeActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startStorageTypeActivity() {
        val intent = Intent(this@MainActivity, StorageTypeActivity::class.java)
        startActivityForResult(intent, 1)
    }

    fun onClick(view: View?) {
        val builder = AlertDialog.Builder(this)
        val editText = EditText(this)
        editText.hint = "Введите название"
        val layout = FrameLayout(this)
        layout.apply {
            addView(editText)
        }
        builder.setTitle("Создание нового файла")
                .setView(layout)
                .setPositiveButton("Создать") { dialog, _ ->  dialog.cancel()
                    val fileName = editText.text.toString()
                    if (fileName.isNotEmpty()) {
                        if (files.contains(fileName)) {
                            toast("Файл с таким именем уже существует")
                        } else {
                            var directory = filesDir
                            var storageType = "internal"
                            if (loadSwitchState()) {
                                if (isExternalStorageExists()) {
                                    directory = getExternalFilesDir(null)
                                    storageType = "external"
                                } else toast("External storage не доступен. Файл будет сохранён в internal storage")
                            }
                            Item.writeText(fileName, "", directory)
                            items.add(Item(fileName, R.drawable.text_file, storageType))
                            files.add(fileName)
                            adapter?.setItems(items)
                        }
                    }
                }
        val dialog: AlertDialog? = builder.create()
        dialog?.show()
    }

    private fun startEditActivity(item: Item, position: Int) {
        val intent = Intent(this@MainActivity, EditFileActivity::class.java)
        intent.putExtra("name", item.name)
        intent.putExtra("position", position)
        intent.putExtra("storageType", item.storageType)
        startActivityForResult(intent, 2)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                val array = data!!.getStringArrayExtra(ACCESS_MESSAGE)
                val fileName = array!![0]
                val action = array[1]
                val text = array!![3]
                if (action.length == 4) {
                    val position = array[2].toInt()
                    var directory = filesDir
                    if (items[position].storageType == "external") {
                        directory = getExternalFilesDir(null)
                    }
                    if (files.contains(fileName)) {
                        if (items[position].name != fileName) {
                            toast("Файл с таким именем уже существует")
                        }
                        Item.writeText(items[position].name, text, directory)
                    } else {
                        Item.removeFile(items[position].name, directory)
                        Item.writeText(fileName, text, directory)
                        items[position].name = fileName
                        files[position] = fileName
                    }
                } else {
                    val position = array[2].toInt()
                    items.removeAt(position)
                    files.removeAt(position)
                }
                adapter?.setItems(items)
            } else {
                toast("Список файлов не изменился")
            }
        }
    }

    private fun loadSwitchState() : Boolean {
        val sharedPrefs = getSharedPreferences(namePreference, Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean("KEY_IS_CHECKED", false)
    }

    private fun isExternalStorageExists() =
            Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    private fun toast(textToast: String) {
        val toast = Toast.makeText(this, textToast, Toast.LENGTH_LONG)
        toast.show()
    }

    companion object {
        const val ACCESS_MESSAGE = "ACCESS_MESSAGE"
    }
}