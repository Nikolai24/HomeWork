package com.example.homework9_2.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.homework9_2.data.HourlyWeather
import com.example.homework9_2.data.Weather
import com.example.homework9_2.data.api.WeatherRepository
import com.example.homework9_2.data.database.CityRepository
import com.example.homework9_2.data.database.CityRepositoryImpl
import com.example.homework9_2.model.HourlyWeatherModel
import com.example.homework9_2.model.HourlyWeatherModelMapper
import com.example.homework9_2.model.WeatherModel
import com.example.homework9_2.model.WeatherModelMapper

class MainActivityViewModel(app: Application) : AndroidViewModel(app) {
    private val context = app.baseContext
    private val weatherModelMapper: WeatherModelMapper = WeatherModelMapper()
    private val hourlyWeatherModelMapper: HourlyWeatherModelMapper= HourlyWeatherModelMapper(context)
    private val degreeListener: DegreeListener = DegreeListenerImpl(context.getSharedPreferences("degreeType", Context.MODE_PRIVATE))
    private val cityRepository: CityRepository = CityRepositoryImpl(context)
    private val cityListener: CityListener = CityListenerImpl(context.getSharedPreferences(CITY, Context.MODE_PRIVATE))
    private val mutableWeatherLiveData = MutableLiveData<WeatherModel>()
    val weatherLiveData = mutableWeatherLiveData
    private val mutableWeatherAdapterLiveData = MutableLiveData<List<HourlyWeatherModel>>()
    val weatherAdapterLiveData = mutableWeatherAdapterLiveData
    fun getMainWeatherFromApi() {
        WeatherRepository.getWeather(
                id = cityListener.loadCity(),
                degree = degreeListener.loadDegree(),
                onSuccess = ::getMainWeather,
                onError = ::onError
        )
    }

    private fun getWeatherForAdapterFromApi(lat: String, lon: String) {
        WeatherRepository.getHourlyWeather(
                lat = lat,
                lon = lon,
                degree = degreeListener.loadDegree(),
                onSuccess = ::getHourlyWeather,
                onError = ::onError
        )
    }

    private fun getHourlyWeather(hourlyWeather: HourlyWeather) {
        weatherAdapterLiveData.value=hourlyWeatherModelMapper.invoke(hourlyWeather.hourly)
    }

    private fun getMainWeather(weather: Weather) {
        cityRepository.insertCityInDb(weather)
        mutableWeatherLiveData.value = weatherModelMapper.invoke(weather)
        getWeatherForAdapterFromApi(weather.coord.lat.toString(), weather.coord.lon.toString())
    }

    private fun onError() {

    }
}