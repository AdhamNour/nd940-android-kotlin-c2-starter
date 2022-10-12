package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel : ViewModel() {
    private val _pictureOfDay = MutableLiveData<PictureOfDay>();
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay
    private val _asteroidArray = MutableLiveData<List<Asteroid>>()
    val asteroidArray: LiveData<List<Asteroid>>
        get() = _asteroidArray
    private val _navigateToAsteroidDetails = MutableLiveData<Long>()
    val navigateToAsteroidDetails
        get() = _navigateToAsteroidDetails
    init {
        getImageOfTheDay()
        getAsteroid()
    }

    private fun getAsteroid() {
        viewModelScope.launch {
            val response = NasaApi.retrofitService.getAsteroid()
            val json = JSONObject(response)
            _asteroidArray.value= parseAsteroidsJsonResult(json)
        }
    }

    private fun getImageOfTheDay() {
        viewModelScope.launch {
            _pictureOfDay.value = NasaApi.retrofitService.getImageOfTheDay()
        }
    }

    fun onAsteroidClicked(id:Long){
        _navigateToAsteroidDetails.value=id;
    }
    fun onAsteroidNavigated() {
        _navigateToAsteroidDetails.value = null
    }
}