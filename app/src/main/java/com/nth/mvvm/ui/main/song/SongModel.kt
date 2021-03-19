package com.nth.mvvm.ui.main.song

import androidx.lifecycle.MutableLiveData
import com.nth.mvvm.data.local.AppDatabase
import com.nth.mvvm.data.model.ItemSong
import com.nth.mvvm.data.remote.ApiHelp
import com.nth.mvvm.data.remote.ApiSong
import com.nth.mvvm.ui.base.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * Created by NguyenTienHoa on 3/13/2021
 */

class SongModel  @Inject constructor(
    appDatabase: AppDatabase,
    schedulers: Executor
) :
    BaseViewModel<SongCallBack>(appDatabase, schedulers) {
    private val api: ApiSong

    val obSong = MutableLiveData<MutableList<ItemSong>>()

    init {
        api = ApiHelp.createRetrofit().create(ApiSong::class.java)
    }

    fun getSongs(name: String): Disposable? {
        setIsLoading(true)
        return subscribeHasResultDispose(
            api.getSongs(name = name)
                .subscribeOn(Schedulers.from(schedulers))
                .observeOn(AndroidSchedulers.mainThread()),
            {
                for (itemSong in it) {
                    itemSong.id = itemSong.id + "@@@" + name
                    itemSong.keySearch = name
                }
                //save database
                makeFunOnOtherThread {
                    appDatabase.songDao().deleteFromKeySearch(name)
                    appDatabase.songDao().insertAll(it)
                }
                setIsLoading(false)
                //send to obSong and obSong call back view register
                obSong.value = it
            },
            {
                setIsLoading(false)
                if (it is UnknownHostException){
                    val  resultSong = appDatabase.songDao()
                        .findAllFromKeySearch(name)
                    if ( resultSong.size > 0){
                        obSong.value = resultSong
                        return@subscribeHasResultDispose
                    }
                }

                callBack?.get()?.error(ItemSong::class.java.name, it)
            })
    }

    fun getAllSongOffline() {
        obSong.value = appDatabase.songDao().findAll()
    }

}