package com.example.hsexercise.businesslogic.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.hsexercise.businesslogic.PhotoModel
import io.reactivex.Maybe

@Dao
interface PhotosDao {
    @Query("SELECT * FROM photomodel")
    fun getAll(): List<PhotoModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(models: List<PhotoModel>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(featureModel: PhotoModel)
}