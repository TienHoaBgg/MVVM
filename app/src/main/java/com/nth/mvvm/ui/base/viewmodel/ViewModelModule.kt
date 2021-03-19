package com.nth.mvvm.ui.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nth.mvvm.di.model.ViewModelFactory
import com.nth.mvvm.di.model.ViewModelKey
import com.nth.mvvm.ui.main.song.SongModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by NguyenTienHoa on 3/13/2021
 */

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SongModel::class)
    abstract fun bindsSongViewModel(songViewModel: SongModel): ViewModel

    @Binds
    abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}