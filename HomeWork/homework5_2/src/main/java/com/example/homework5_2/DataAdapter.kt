package com.example.homework5_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface OnItemClickListener {
    fun onItemClick(item: Item?, position: Int)
}

class DataAdapter(items: MutableList<Item>, itemsAll: MutableList<Item>, listener: OnItemClickListener) : RecyclerView.Adapter<DataAdapter.ViewHolder>(), Filterable {
    private val items: MutableList<Item>
    private val itemsAll: MutableList<Item>
    private val listener: OnItemClickListener
    fun setItems(itemsNew: List<Item>) {
        items.clear()
        itemsAll.clear()
        items.addAll(itemsNew)
        itemsAll.addAll(itemsNew)
        notifyDataSetChanged()
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
        val item: Item = items!![position]
        holder.nameView.setText(item.name)
        holder.contactView.setText(item.contact)
        holder.imageView.setImageResource(item.image)
        holder.itemView.setOnClickListener { listener.onItemClick(item, position) }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun getFilter(): Filter {
        return myFilter
    }

    var myFilter: Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filteredList: MutableList<Item> = ArrayList<Item>()
            if (charSequence == null || charSequence.length == 0) {
                filteredList.addAll(itemsAll)
            } else {
                for (movie in itemsAll) {
                    if (movie.name.toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(movie)
                    }
                }
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

    init {
        this.items = ArrayList<Item>(items)
        this.itemsAll = ArrayList<Item>(items)
        this.listener = listener
    }
}