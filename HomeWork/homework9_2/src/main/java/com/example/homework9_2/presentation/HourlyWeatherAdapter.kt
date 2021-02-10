package com.example.homework9_2.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework9_2.databinding.WeatherRecyclerBinding
import com.example.homework9_2.model.HourlyWeatherModel

class HourlyWeatherAdapter(
        private var hourlyWeather: MutableList<HourlyWeatherModel> = mutableListOf()
) : RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            HourlyWeatherHolder(WeatherRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: HourlyWeatherHolder, position: Int) {
        holder.bind(hourlyWeather[position])
    }

    override fun getItemCount() = hourlyWeather.size

    fun update(list: List<HourlyWeatherModel>) {
        hourlyWeather = list.toMutableList()
        notifyDataSetChanged()
    }

    class HourlyWeatherHolder(private val binding: WeatherRecyclerBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(hourly: HourlyWeatherModel) {
            with(binding) {
                degree.text = hourly.temp
                wind.text = hourly.wind_speed
                time.text = hourly.time
            }
        }
    }
}