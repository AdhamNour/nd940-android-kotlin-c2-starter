package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Room

private lateinit var instance: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {

        if (!::instance.isInitialized) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                AsteroidDatabase::class.java,
                "videos"
            ).build()
        }
        return instance;
    }
}