package com.example.edu.seho.myhome.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.edu.seho.myhome.data.ShoppingCartDatabase
import com.example.edu.seho.myhome.model.ShoppingCart
import com.example.edu.seho.myhome.repository.ShoppingCartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/** @author Sebastian Holm
 *  Handles the communication between the fragment and the shoppingCart repository
 *  or in other words the database.
 */

class ShoppingCartViewModel(application : Application) : AndroidViewModel(application) {

    var readAllData : LiveData<List<ShoppingCart>>
    private val repository : ShoppingCartRepository

    init {
        val shoppingCartDao = ShoppingCartDatabase.getDatabase(application).shoppingCartDao()
        repository = ShoppingCartRepository(shoppingCartDao)
        readAllData = repository.readAllData
    }

    fun addToCart(shoppingCart: ShoppingCart){
        viewModelScope.launch(Dispatchers.IO){
            repository.addToCart(shoppingCart)
        }
    }

    fun updateShoppingCart(shoppingCart: ShoppingCart){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateShoppingCart(shoppingCart)
        }
    }

    fun deleteAllShoppingCart(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllShoppingCart()
        }
    }
}