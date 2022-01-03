package com.example.edu.seho.myhome.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.edu.seho.myhome.enumClasses.Order
import com.example.edu.seho.myhome.data.StorageDatabase
import com.example.edu.seho.myhome.repository.StorageRepository
import com.example.edu.seho.myhome.model.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/** @author Sebastian Holm
 *  Handles the communication between the fragments and the storage repository
 *  or in other words the database.
 */

class StorageViewModel(application : Application) : AndroidViewModel(application){

    var readAllData : LiveData<List<Storage>>
    private val repository : StorageRepository

    init {
        val storageDao = StorageDatabase.getDatabase(application).storageDao()
        repository = StorageRepository(storageDao)
        readAllData = repository.readAllData
    }

    fun changeOrdering(order : Order){
        repository.changeOrder(order)
        readAllData = repository.readAllData
    }

    fun addStorage(storage : Storage){
        viewModelScope.launch(Dispatchers.IO){
            repository.addStorage(storage)
        }
    }

    fun updateStorage(storage : Storage){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateStorage(storage)
        }
    }

    fun deleteStorage(storage : Storage){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteStorage(storage)
        }
    }

    fun deleteAllStorage(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllStorage()
        }
    }
}