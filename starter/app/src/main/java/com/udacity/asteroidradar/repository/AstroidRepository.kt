package com.udacity.asteroidradar.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDatabaseAstroid
import com.udacity.asteroidradar.database.asDomainAstroids
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import timber.log.Timber

class AstroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.astroidDao.getAllAstroids()) {
            it.asDomainAstroids()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {

                val response = NasaApi.retrofitService.getAsteroid()
                val json = JSONObject(response)
                val asteroids = parseAsteroidsJsonResult(json)
                database.astroidDao.insertAll(*asteroids.asDatabaseAstroid())
            } catch (_: Exception) {

            }
        }
    }
}