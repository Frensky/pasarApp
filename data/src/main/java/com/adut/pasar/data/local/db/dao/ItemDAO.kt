package com.adut.pasar.data.local.db.dao

import androidx.room.*
import com.adut.pasar.data.model.ItemEntity

@Dao
interface ItemDAO {
    @Query("select * from ItemEntity")
    suspend fun getAllItem(): List<ItemEntity>

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
    @Query("select * from JuloApplication where id=" +
            "(SELECT value FROM JuloConfig WHERE `key` = '" + Key.KEY_CURRENT_APP_ID + "')")
    suspend fun getApplication(): JuloApplication

    @get:Query("select * from JuloApplication where locId=" +
            "(SELECT value FROM JuloConfig WHERE `key` = '" + Key.KEY_CURRENT_LOC_ID + "')")
    val locApplication: JuloApplication

    @Query("select * from JuloApplication")
    suspend fun getAllApplications(): List<JuloApplication>

    @get:Deprecated("")
    @get:Query("select id from JuloApplication")
    val applicationId: Long

    @Query("select * from JuloApplication where id = :applicationId")
    fun getApplication(applicationId: Long): JuloApplication

//    @Query("select * from JuloApplication where id = :applicationId")
//    fun getApplicationLiveData(applicationId: Long): LiveData<JuloApplication>

    @Query("select status from JuloApplication where id = :applicationId")
    suspend fun getApplicationStatus(applicationId: Long): Int

  */
}