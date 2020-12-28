package com.example.homework6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface OnItemClickListener {
    fun onItemClick(item: Item?, position: Int)
}

class DataAdapter(items: MutableList<Item>, listener: OnItemClickListener) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    private val items: MutableList<Item>
    private val listener: OnItemClickListener
    fun setItems(itemsNew: List<Item>) {
        items.clear()
        items.addAll(itemsNew)
        notifyDataSetChanged()
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.name)
        val imageView: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Item = items[position]
        holder.nameView.text = item.name
        holder.imageView.setImageResource(item.image)
        holder.itemView.setOnClickListener { listener.onItemClick(item, position) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    init {
        this.items = ArrayList<Item>(items)
        this.listener = listener
    }
}