package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AstroidRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber

class MainViewModel (application: Application) : AndroidViewModel(application) {
    private val _pictureOfDay = MutableLiveData<PictureOfDay>();
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay
    private val database = getDatabase(application);
    private val asteroidRepository = AstroidRepository(database)
    val asteroidArray: LiveData<List<Asteroid>>
        get() = asteroidRepository.asteroids

    init {
        getImageOfTheDay()
        getAsteroid()
    }

    private fun getAsteroid() {
        viewModelScope.launch {
            asteroidRepository.refreshAsteroids()
        }
    }

    private fun getImageOfTheDay() {
        viewModelScope.launch {
            _pictureOfDay.value = try {
                NasaApi.retrofitService.getImageOfTheDay()
            }catch(e: Exception) {
                PictureOfDay("error","Connection Error","")
            }
        }
    }


}