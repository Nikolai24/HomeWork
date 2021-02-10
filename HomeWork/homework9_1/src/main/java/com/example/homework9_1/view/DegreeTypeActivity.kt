package com.example.homework9_1.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework9_1.R
import com.example.homework9_1.databinding.ActivityDegreeTypeBinding
import com.example.homework9_1.presentation.DegreeListener
import com.example.homework9_1.presentation.DegreeListenerImpl

class DegreeTypeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDegreeTypeBinding
    private val degreeListener: DegreeListener by lazy { DegreeListenerImpl(getSharedPreferences("degreeType", Context.MODE_PRIVATE)) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDegreeTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            degree.apply {
                isChecked = degreeListener.loadDegree() == "metric"
                if (isChecked) {
                    setText(R.string.celsius)
                } else {
                    setText(R.string.fahrenheit)
                }
                setOnCheckedChangeListener { item, isChecked ->
                    if (isChecked) {
                        item.setText(R.string.celsius)
                    } else {
                        item.setText(R.string.fahrenheit)
                    }
                }
            }
            selectButton.setOnClickListener {
                degreeListener.saveSettings(degree.isChecked)
                finish()
            }
        }
    }
}