package com.nth.mvvm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nth.mvvm.data.local.dao.SongDao
import com.nth.mvvm.data.model.ItemSong

/**
 * Created by NguyenTienHoa on 3/13/2021
 */

@Database(entities = [ItemSong::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
}