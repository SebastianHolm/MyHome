package com.example.edu.seho.myhome.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.edu.seho.myhome.model.Storage

/** @author Sebastian Holm
 *  This Dao is an interface that handles communication between
 *  the database and the repository for the database handling the Storage class.
 */


@Dao
interface StorageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addStorage(storage : Storage)

    @Update
    suspend fun updateStorage(storage : Storage)

    @Delete
    suspend fun deleteStorage(storage : Storage)

    @Query("DELETE FROM storage_table")
    suspend fun deleteAllStorage()

    /**
     * There is multiple readAllData classes for the repository to call depending on
     * the ordering set for display in the ListFragment class.
     */

    @Query("SELECT * FROM storage_table ORDER BY id ASC")
    fun readAllData() : LiveData<List<Storage>>

    @Query("SELECT * FROM storage_table ORDER BY category ASC")
    fun readAllDataCategory() : LiveData<List<Storage>>

    @Query("SELECT * FROM storage_table ORDER BY date ASC")
    fun readAllDataDate() : LiveData<List<Storage>>

    @Query("SELECT * FROM storage_table ORDER BY name ASC")
    fun readAllDataName() : LiveData<List<Storage>>
}