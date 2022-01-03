package com.example.edu.seho.myhome.repository

import androidx.lifecycle.LiveData
import com.example.edu.seho.myhome.enumClasses.Order
import com.example.edu.seho.myhome.data.StorageDao
import com.example.edu.seho.myhome.model.Storage

/** @author Sebastian Holm
 *  Handles the communication between the storage view model and the storage Dao.
 *  It also calls different readAll functions depending on what type of ordering is
 *  set in the storage View model.
 */

class StorageRepository(private val storageDao : StorageDao) {

    var readAllData : LiveData<List<Storage>> = storageDao.readAllData()

    fun changeOrder(order : Order){
        readAllData = when (order) {
            Order.Category -> storageDao.readAllDataCategory()
            Order.Date -> storageDao.readAllDataDate()
            Order.Name -> storageDao.readAllDataName()
            else -> storageDao.readAllData()
        }
    }

    suspend fun addStorage(storage : Storage){
        storageDao.addStorage(storage)
    }

    suspend fun updateStorage(storage : Storage){
        storageDao.updateStorage(storage)
    }

    suspend fun deleteStorage(storage : Storage){
        storageDao.deleteStorage(storage)
    }

    suspend fun deleteAllStorage(){
        storageDao.deleteAllStorage()
    }
}