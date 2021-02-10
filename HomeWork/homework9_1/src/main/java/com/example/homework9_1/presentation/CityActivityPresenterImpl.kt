package com.example.homework9_1.presentation

import android.annotation.SuppressLint
import android.content.Context
import com.example.homework9_1.data.database.CityRepository
import com.example.homework9_1.data.database.CityRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers

class CityActivityPresenterImpl(
        context: Context,
        private val cityRepository: CityRepository = CityRepositoryImpl(context),
        private val cityListener: CityListener = CityListenerImpl(context.getSharedPreferences(CITY, Context.MODE_PRIVATE))
) : CityActivityPresenter {
    @SuppressLint("CheckResult")
    override fun updateAdapter(adapter: CityAdapter) {
        cityRepository.getCitiesFromDb()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { adapter.update(it) }
    }

    override fun setCity(name: String) {
        cityListener.saveCity(name)
    }

}