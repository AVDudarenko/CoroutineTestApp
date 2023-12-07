package com.example.coroutinetestapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.coroutinetestapp.databinding.ActivityMainBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

	private val binding by lazy {
		ActivityMainBinding.inflate(layoutInflater)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
		binding.buttonLoad.setOnClickListener {
			binding.progress.isVisible = true
			binding.buttonLoad.isEnabled = false
			val deferredCity = lifecycleScope.async {
				val city = loadCity()
				city
			}
			val deferredTemperature = lifecycleScope.async {
				val temperature = loadTemperature()
				temperature
			}
			lifecycleScope.launch {
				val city = deferredCity.await()
				binding.tvLocation.text = city
				val temp = deferredTemperature.await()
				binding.tvTemperature.text = temp.toString()
				Toast.makeText(
					this@MainActivity,
					"city: $city, temp: $temp",
					Toast.LENGTH_SHORT
				).show()
				binding.progress.isVisible = false
				binding.buttonLoad.isEnabled = true
			}
		}
	}

	private suspend fun loadData() {
		binding.progress.isVisible = true
		binding.buttonLoad.isEnabled = false
		val city = loadCity()
		binding.tvLocation.text = city
		val temperature = loadTemperature()
		binding.tvTemperature.text = temperature.toString()
		binding.progress.isVisible = false
		binding.buttonLoad.isEnabled = true
	}


	private suspend fun loadCity(): String {
		delay(2000)
		return "Minsk"
	}

	private suspend fun loadTemperature(): Int {
		delay(5000)
		return -1
	}
}