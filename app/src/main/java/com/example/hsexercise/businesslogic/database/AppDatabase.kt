package com.example.hsexercise.businesslogic.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.hsexercise.businesslogic.PhotoModel

@Database(entities = [(PhotoModel::class)], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photosDao(): PhotosDao

    companion object {

        private var sInstance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {

                sInstance = Room
                        .databaseBuilder(context.applicationContext, AppDatabase::class.java,
                            DATABASE_NAME
                        )
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return sInstance!!
        }

        const val DATABASE_NAME = "headspace-database"
    }

}
