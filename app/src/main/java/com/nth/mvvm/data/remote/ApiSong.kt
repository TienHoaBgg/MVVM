package com.nth.mvvm.data.remote

import com.nth.mvvm.data.model.ItemSong
import com.nth.mvvm.data.model.LinkSong
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiSong {
    @GET("/api/searchSong")
    fun getSongs(
        @Query(value = "songName") name: String
    ): Observable<MutableList<ItemSong>>

    @GET("/api/linkMusic")
    fun getLink(
        @Query(value = "linkSong") linkSong: String
    ): Observable<LinkSong>
}