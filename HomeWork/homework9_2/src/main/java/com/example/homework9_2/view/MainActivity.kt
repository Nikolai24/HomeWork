package com.example.homework9_2.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework9_2.R
import com.example.homework9_2.databinding.ActivityMainBinding
import com.example.homework9_2.model.HourlyWeatherModel
import com.example.homework9_2.model.WeatherModel
import com.example.homework9_2.presentation.HourlyWeatherAdapter
import com.example.homework9_2.presentation.MainActivityViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val weatherAdapter by lazy { HourlyWeatherAdapter() }
    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
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
        mainActivityViewModel.getMainWeatherFromApi()
    }

    private fun initViewModel() {
        mainActivityViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.application).create(MainActivityViewModel::class.java)
        mainActivityViewModel.weatherLiveData.observe(this, Observer<WeatherModel> { weatherModel ->
            with(binding) {
                city.text = weatherModel.cityName
                degree.text = weatherModel.temp
                description.text = weatherModel.description
            }
        })
        mainActivityViewModel.weatherAdapterLiveData
                .observe(this, Observer<List<HourlyWeatherModel>> { hourlyweather -> weatherAdapter.update(hourlyweather) })
    }
}