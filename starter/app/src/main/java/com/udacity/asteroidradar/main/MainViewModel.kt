package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AstroidRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel (application: Application) : AndroidViewModel(application) {
    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay
    private val database = getDatabase(application)
    private val asteroidRepository = AstroidRepository(database)

    private val todayFilter = MutableLiveData(false)
    private val weekFilter = MutableLiveData(false)

    private val _asteroidArray: LiveData<List<Asteroid>> = asteroidRepository.asteroids

    private val _filteredAsterids = MutableLiveData<List<Asteroid>>()

    val asteroidArray: LiveData<List<Asteroid>>
        get() = _filteredAsterids

    init {
        getImageOfTheDay()
        todayFilter.observeForever { filterAsteroids() }
        weekFilter.observeForever { filterAsteroids() }
        _asteroidArray.observeForever { filterAsteroids() }
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

     fun toggleFilterToday (){
        todayFilter.value= !todayFilter.value!!
    }


    fun toggleWeekFilter(){
        weekFilter.value = !weekFilter.value!!
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun filterAsteroids(){
        val filteredAsteroids = mutableListOf<Asteroid>()
        if(todayFilter.value == true)
            _asteroidArray.value?.forEach {
            val localDate = LocalDate.parse(it.closeApproachDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            if(localDate.equals(LocalDate.now()))
                filteredAsteroids.add(it)
        }
        else if (weekFilter.value== true)
            _asteroidArray.value?.forEach {
                val localDate = LocalDate.parse(it.closeApproachDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                if(localDate.isAfter(LocalDate.now().minusDays(7)))
                    filteredAsteroids.add(it)
            }
        else _asteroidArray.value?.forEach {
            filteredAsteroids.add(it)
        }
    _filteredAsterids.value=filteredAsteroids
    }
}