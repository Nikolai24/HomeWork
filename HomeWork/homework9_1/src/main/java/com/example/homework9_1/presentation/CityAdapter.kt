package com.example.homework9_1.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework9_1.data.database.City
import com.example.homework9_1.databinding.CityRecyclerBinding

class CityAdapter(
        private val onClickListener: CityOnClickListener,
        private var cityList: MutableList<City> = mutableListOf()
) : RecyclerView.Adapter<CityAdapter.CityHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = CityHolder(CityRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(cityList[position])
        holder.itemView.setOnClickListener { onClickListener.onClick(cityList[position].city) }
    }

    override fun getItemCount()=cityList.size

    fun update(dbList:List<City>){
        cityList=dbList.toMutableList()
        notifyDataSetChanged()
    }

    class CityHolder(private val binding: CityRecyclerBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind (city: City){
            binding.cityName.text=city.city
        }
    }
}