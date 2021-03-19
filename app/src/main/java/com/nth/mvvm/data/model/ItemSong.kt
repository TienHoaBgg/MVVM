package com.nth.mvvm.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ItemSong")
data class ItemSong(
        @PrimaryKey
        @ColumnInfo(name = "id")
        var id: String = "",

        @ColumnInfo(name = "keySearch")
        var keySearch: String = "",

        @ColumnInfo(name = "songName")
        @SerializedName("songName")
        var songName: String? = null,

        @ColumnInfo(name = "artistName")
        @SerializedName("artistName")
        var artistName: String? = null,

        @ColumnInfo(name = "linkSong")
        @SerializedName("linkSong")
        var linkSong: String? = null,

        @ColumnInfo(name = "linkImage")
        @SerializedName("linkImage")
        var linkImage: String? = null,

        @ColumnInfo(name = "linkMusic")
        @SerializedName("linkMusic")
        var linkMusic: String? = null

) {
}