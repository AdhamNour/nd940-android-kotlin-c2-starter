package com.udacity.asteroidradar.database

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [DatabaseAstroid::class], version = 1)
abstract class AsteroidDatabase:RoomDatabase() {

    abstract val astroidDao:AstroidDao
}