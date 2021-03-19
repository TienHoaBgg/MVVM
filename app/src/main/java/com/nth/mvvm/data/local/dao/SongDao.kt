package com.nth.mvvm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nth.mvvm.data.model.ItemSong

@Dao
interface SongDao {
    @Query(value = "SELECT * FROM ItemSong WHERE ItemSong.id = :songId")
    fun findOne(songId: Int): ItemSong

    @Query(value = "SELECT * FROM ItemSong")
    fun findAll(): MutableList<ItemSong>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(item: ItemSong): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: MutableList<ItemSong>)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(itemSong: MutableList<ItemSong>): MutableList<Long>

    @Query(value = "SELECT * FROM ItemSong WHERE ItemSong.keySearch = :keySearch")
    fun findAllFromKeySearch(keySearch: String):MutableList<ItemSong>

    @Query(value = "DELETE FROM ItemSong WHERE ItemSong.keySearch = :keySearch")
    fun deleteFromKeySearch(keySearch: String)
}