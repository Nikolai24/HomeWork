package com.example.homework9_2.presentation

import android.annotation.SuppressLint
import android.content.SharedPreferences

class DegreeListenerImpl(private val pref: SharedPreferences) : DegreeListener {
    @SuppressLint("CommitPrefEdits")
    override fun saveSettings(isMetric: Boolean) {
        with(pref.edit()) {
            if (isMetric) {
                putString("degreeType", "metric")
            } else {
                putString("degreeType", "imperial")
            }
        }.apply()
    }

    override fun loadDegree() = pref.getString("degreeType", "metric") ?: ""
}