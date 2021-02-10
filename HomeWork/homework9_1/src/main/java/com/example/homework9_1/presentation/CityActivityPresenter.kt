package com.example.homework9_1.presentation

interface CityActivityPresenter {
    fun updateAdapter(adapter: CityAdapter)
    fun setCity(name: String)
}