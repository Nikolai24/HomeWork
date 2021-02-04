package com.example.homework5_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataAdapter(items: MutableList<Item>, listener: OnItemClickListener) : RecyclerView.Adapter<DataAdapter.ViewHolder>(), Filterable {
    private val items: MutableList<Item>
    private var itemsAll: MutableList<Item>
    private val listener: OnItemClickListener

    init {
        this.items = ArrayList(items)
        this.itemsAll = ArrayList(items)
        this.listener = listener
    }

    fun setItems(itemsNew: List<Item>) {
        items.clear()
        itemsAll.clear()
        items.addAll(itemsNew)
        itemsAll.addAll(itemsNew)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(item: Item, position: Int)
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.name)
        val contactView: TextView  = itemView.findViewById(R.id.contact)
        val imageView: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Item = items[position]
        holder.nameView.text = item.name
        holder.contactView.text = item.contact
        if (item.image == "email") {
            holder.imageView.setImageResource(R.drawable.email)
        } else {
            holder.imageView.setImageResource(R.drawable.phone)
        }
        holder.itemView.setOnClickListener { listener.onItemClick(item, position) }
    }

    override fun getItemCount() = items.size

    override fun getFilter() = myFilter

    private var myFilter: Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            var filteredList: MutableList<Item> = mutableListOf()
            if (charSequence.isEmpty()) {
                filteredList.addAll(itemsAll)
            } else {
                filteredList = itemsAll.filter{ item -> item.name.toLowerCase().contains(charSequence.toString().toLowerCase())}.toMutableList()
            }
            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }
        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            items.clear()
            items.addAll((filterResults.values as Collection<Item>))
            notifyDataSetChanged()
        }
    }
}