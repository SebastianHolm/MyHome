package com.example.edu.seho.myhome.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.edu.seho.myhome.model.ShoppingCart

@Database(entities = [ShoppingCart::class], version = 1, exportSchema = false)
abstract class ShoppingCartDatabase : RoomDatabase() {
    abstract fun shoppingCartDao() : ShoppingCartDao

    companion object{

        @Volatile
        private var INSTANCE : ShoppingCartDatabase? = null

        fun getDatabase(context : Context) : ShoppingCartDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingCartDatabase::class.java,
                    "shoppingCart_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}