package com.example.homework9_2.data.api

import android.util.Log
import com.example.homework9_2.data.HourlyWeather
import com.example.homework9_2.data.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherRepository {
    private val weatherApi: WeatherApi

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        weatherApi = retrofit.create(WeatherApi::class.java)
    }

    fun getWeather(
            id: String,
            degree:String,
            onSuccess: (weather: Weather) -> Unit,
            onError: () -> Unit
    ) {
        weatherApi.getWeather(id=id, degree=degree)
                .enqueue(object : Callback<Weather> {
                    override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                onSuccess.invoke(responseBody)
                            }
                        } else {
                            onError.invoke()
                        }
                    }

                    override fun onFailure(call: Call<Weather>, t: Throwable) {
                        onError.invoke()
                    }
                })

    }

    fun getHourlyWeather(
            degree: String,
            lat:String,
            lon: String,
            onSuccess: (weather: HourlyWeather) -> Unit,
            onError: () -> Unit
    ) {
        weatherApi.getHourlyWeather(degree = degree, lat=lat, lon = lon)
                .enqueue(object : Callback<HourlyWeather> {
                    override fun onResponse(call: Call<HourlyWeather>, response: Response<HourlyWeather>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                onSuccess.invoke(responseBody)
                            } else {
                                onError.invoke()
                            }
                        }
                    }

                    override fun onFailure(call: Call<HourlyWeather>, t: Throwable) {
                        Log.d("HM9", "$t")
                        onError.invoke()
                    }
                })
    }
}