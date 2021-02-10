package com.example.homework9_1.view

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework9_1.databinding.ActivityCityBinding
import com.example.homework9_1.presentation.CityActivityPresenter
import com.example.homework9_1.presentation.CityActivityPresenterImpl
import com.example.homework9_1.presentation.CityAdapter
import com.example.homework9_1.presentation.CityOnClickListener

class CityActivity : AppCompatActivity(), CityOnClickListener {
    private lateinit var binding: ActivityCityBinding
    private val cityAdapter by lazy { CityAdapter(this) }
    private val cityPresenter: CityActivityPresenter by lazy { CityActivityPresenterImpl(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        cityPresenter.updateAdapter(cityAdapter)
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
                        cityPresenter.setCity(input.text.toString())
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

    override fun onClick(name: String) {
        cityPresenter.setCity(name)
        finish()
    }
}