package com.adut.pasar.data.local.db.dao

import androidx.room.*
import com.adut.pasar.data.model.ItemEntity

@Dao
interface ItemDAO {
    @Query("select * from ItemEntity")
    suspend fun getTopItems(): List<ItemEntity>

    @Query("SELECT * from ItemEntity WHERE title LIKE '%'||:keyword||'%' ")
    suspend fun getItemsByKeyWord(keyword:String): List<ItemEntity>

    @Query("SELECT title from ItemEntity WHERE title LIKE '%'||:keyword||'%' ")
    suspend fun getTitleByKeyWord(keyword:String): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItem(item: ItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveItems(items: List<ItemEntity>)

    @Update
    suspend fun updateItem(items: List<ItemEntity>)

    @Update
    suspend fun updateItem(items: ItemEntity)

    @Query("delete from ItemEntity")
    suspend fun deleteAll()

    /*

    @Query("select * from JuloApplication")
    suspend fun getAllApplications(): List<JuloApplication>

    @Query("SELECT name FROM companies WHERE name LIKE '%'||:keyword||'%' ")
    fun getCompanies(keyword: String): List<String>

    @Query("select * from JuloApplication where id = :applicationId")
    fun getApplication(applicationId: Long): JuloApplication

//    @Query("select * from JuloApplication where id = :applicationId")
//    fun getApplicationLiveData(applicationId: Long): LiveData<JuloApplication>

    @Query("select status from JuloApplication where id = :applicationId")
    suspend fun getApplicationStatus(applicationId: Long): Int

  */
}