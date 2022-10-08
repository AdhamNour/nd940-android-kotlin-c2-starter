package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AstroidDao {
    @Query("select * from DatabaseAstroid")
    fun getAllAstroids():LiveData<List<DatabaseAstroid>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg astroids: DatabaseAstroid)

}