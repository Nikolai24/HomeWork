package com.example.homework9_1.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework9_1.R
import com.example.homework9_1.databinding.ActivityMainBinding
import com.example.homework9_1.model.HourlyWeatherModel
import com.example.homework9_1.model.WeatherModel
import com.example.homework9_1.presentation.HourlyWeatherAdapter
import com.example.homework9_1.presentation.MainActivityListener
import com.example.homework9_1.presentation.MainActivityPresenter
import com.example.homework9_1.presentation.MainActivityPresenterImpl

class MainActivity : AppCompatActivity(), MainActivityListener {
    private lateinit var binding: ActivityMainBinding
    private val mainActivityPresenter: MainActivityPresenter by lazy { MainActivityPresenterImpl(this, baseContext) }
    private val weatherAdapter by lazy { HourlyWeatherAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            recycler.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = weatherAdapter
            }
            cityButton.setOnClickListener { startActivity(Intent(this@MainActivity, CityActivity::class.java)) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_storage) {
            startDegreeTypeActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startDegreeTypeActivity() {
        val intent = Intent(this@MainActivity, DegreeTypeActivity::class.java)
        startActivityForResult(intent, 1)
    }

    override fun onStart() {
        super.onStart()
        mainActivityPresenter.getMainWeatherFromApi()
    }

    override fun getHourlyWeather(hourlyWeatherModel: List<HourlyWeatherModel>) {
        weatherAdapter.update(hourlyWeatherModel)
    }

    override fun getMainWeather(weatherModel: WeatherModel) {
        with(binding) {
            city.text = weatherModel.cityName
            degree.text = weatherModel.temp
            description.text = weatherModel.description
        }
    }

    override fun onError() {
        Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
    }
}