package com.example.homework9_2.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.homework9_2.data.database.City
import com.example.homework9_2.data.database.CityRepository
import com.example.homework9_2.data.database.CityRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers

class CityActivityViewModel(app:Application): AndroidViewModel(app) {
    private val context=app.baseContext
    private val cityRepository: CityRepository = CityRepositoryImpl(context)
    private val cityListener: CityListener = CityListenerImpl(context.getSharedPreferences(CITY, Context.MODE_PRIVATE))
    private val mutableCityLiveData= MutableLiveData<List<City>>()
    val cityLiveData=mutableCityLiveData
   @SuppressLint("CheckResult")
   fun updateAdapter(adapter: CityAdapter) {
        cityRepository.getCitiesFromDb()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { cityLiveData.value=it }
    }

   fun setCity(name: String) {
        cityListener.saveCity(name)
    }
}