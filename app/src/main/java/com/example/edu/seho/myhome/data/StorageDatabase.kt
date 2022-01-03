package com.example.edu.seho.myhome.data

import android.content.Context
import androidx.room.*
import com.example.edu.seho.myhome.model.Storage

@Database(entities = [Storage::class], version = 1, exportSchema = false)
abstract class StorageDatabase : RoomDatabase() {

    abstract fun storageDao() : StorageDao

    companion object{

        @Volatile
        private var INSTANCE : StorageDatabase? = null

        fun getDatabase(context : Context) : StorageDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StorageDatabase::class.java,
                    "storage_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}