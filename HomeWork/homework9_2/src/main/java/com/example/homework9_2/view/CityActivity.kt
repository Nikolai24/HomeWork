package com.example.homework9_2.view

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework9_2.databinding.ActivityCityBinding
import com.example.homework9_2.presentation.CityActivityViewModel
import com.example.homework9_2.presentation.CityAdapter
import com.example.homework9_2.presentation.CityOnClickListener

class CityActivity : AppCompatActivity(), CityOnClickListener {
    private lateinit var binding: ActivityCityBinding
    private val cityAdapter by lazy { CityAdapter(this) }
    private lateinit var cityActivityViewModel: CityActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initCityModel()
        with(binding) {
            recyclerCity.apply {
                layoutManager = LinearLayoutManager(this@CityActivity)
                adapter = cityAdapter
            }
            newCityButton.setOnClickListener {
                startDialog()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        cityActivityViewModel.updateAdapter(cityAdapter)
    }

    private fun startDialog() {
        val input = EditText(this@CityActivity)
        with(AlertDialog.Builder(this)) {
            setCancelable(false)
            setTitle("Enter the city name")
            setView(input)
            setPositiveButton("Done") { dialog, which ->
                this.run {
                    if (input.text.isNotEmpty()) {
                        cityActivityViewModel.setCity(input.text.toString())
                        finish()
                    } else {
                        Toast.makeText(this@CityActivity, "City not added", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            setNegativeButton("Cancel") {dialog, which ->
                Toast.makeText(this@CityActivity, "City not added ", Toast.LENGTH_SHORT).show()
            }
            show()
        }
    }

    private fun initCityModel() {
        cityActivityViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.application).create(CityActivityViewModel::class.java)
        cityActivityViewModel.cityLiveData.observe(this, Observer { item -> cityAdapter.update(item) })
    }

    override fun onClick(name: String) {
        cityActivityViewModel.setCity(name)
        finish()
    }
}