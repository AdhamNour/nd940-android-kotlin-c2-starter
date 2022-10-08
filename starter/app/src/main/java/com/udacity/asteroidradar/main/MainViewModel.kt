package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NasaApi
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _pictureOfDay = MutableLiveData<PictureOfDay>();
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay
    init {
        getImageOfTheDay()
        getAsteroid()
    }

    private fun getAsteroid() {
        viewModelScope.launch {
            val x = NasaApi.retrofitService.getAsteroid()
            Log.d("Adham", x.toString())
        }
    }

    private fun getImageOfTheDay() {
        viewModelScope.launch {
            _pictureOfDay.value = NasaApi.retrofitService.getImageOfTheDay()
        }
    }
}