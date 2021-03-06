package com.adut.pasar.data.local.db.dao

import androidx.room.*
import com.adut.pasar.data.model.ItemEntity

@Dao
interface ItemDAO {
    @Query("select * from ItemEntity ORDER BY title ASC")
    suspend fun getTopItems(): List<ItemEntity>

    @Query("SELECT * from ItemEntity WHERE title LIKE '%'||:keyword||'%' ORDER BY title ASC")
    suspend fun getItemsByKeyWord(keyword:String): List<ItemEntity>

    @Query("SELECT title from ItemEntity WHERE title LIKE '%'||:keyword||'%' ")
    suspend fun getTitleByKeyWord(keyword:String): List<String>

    @Query("select * from ItemEntity WHERE isBookMark = 1 ORDER BY title ASC")
    suspend fun getFavoriteItems(): List<ItemEntity>

    @Query("SELECT * from ItemEntity WHERE title LIKE '%'||:keyword||'%' AND isBookMark = 1 ORDER BY title ASC")
    suspend fun getFavItemsByKeyWord(keyword:String): List<ItemEntity>

    @Query("SELECT title from ItemEntity WHERE title LIKE '%'||:keyword||'%' AND isBookMark = 1 ")
    suspend fun getFavTitleByKeyWord(keyword:String): List<String>

    @Query("SELECT * from ItemEntity WHERE id = :itemId")
    suspend fun getItemById(itemId:Long): ItemEntity?

    @Query("SELECT * from ItemEntity WHERE barCodeId = :keyword")
    suspend fun getItemsByBarCode(keyword:String): List<ItemEntity>

    @Query("SELECT DISTINCT quantityType from ItemEntity ORDER BY quantityType ASC")
    suspend fun getQuantityType(): List<String>

    @Query("SELECT id from ItemEntity ORDER BY id DESC")
    suspend fun getIdDesc(): List<Long>

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

    @Query("delete from ItemEntity where id = :id")
    suspend fun deleteByItemID(id:Long)
}