package com.example.edu.seho.myhome.repository

import androidx.lifecycle.LiveData
import com.example.edu.seho.myhome.data.ShoppingCartDao
import com.example.edu.seho.myhome.model.ShoppingCart

/** @author Sebastian Holm
 *  Handles the communication between the shoppingCart view model and the shoppingCart Dao
 */

class ShoppingCartRepository(private val shoppingCartDao : ShoppingCartDao)  {
    var readAllData : LiveData<List<ShoppingCart>> = shoppingCartDao.readAllData()

    suspend fun addToCart(shoppingCart : ShoppingCart){
        shoppingCartDao.addToCart(shoppingCart)
    }

    suspend fun updateShoppingCart(shoppingCart : ShoppingCart){
        shoppingCartDao.updateShoppingCart(shoppingCart)
    }

    suspend fun deleteShoppingCart(shoppingCart : ShoppingCart){
        shoppingCartDao.deleteShoppingCart(shoppingCart)
    }

    suspend fun deleteAllShoppingCart(){
        shoppingCartDao.deleteAllShoppingCart()
    }
}