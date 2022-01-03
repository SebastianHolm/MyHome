package com.example.edu.seho.myhome.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.edu.seho.myhome.model.ShoppingCart

/** @author Sebastian Holm
 * This Dao is an interface that handles communication between
 * the database and the repository for the database handling the ShoppingCart class.
 */

@Dao
interface ShoppingCartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToCart(shoppingCart : ShoppingCart)

    @Update
    suspend fun updateShoppingCart(shoppingCart : ShoppingCart)

    @Delete
    suspend fun deleteShoppingCart(shoppingCart : ShoppingCart)

    @Query("DELETE FROM shoppingCart_table")
    suspend fun deleteAllShoppingCart()

    @Query("SELECT * FROM shoppingCart_table ORDER BY name ASC")
    fun readAllData() : LiveData<List<ShoppingCart>>
}