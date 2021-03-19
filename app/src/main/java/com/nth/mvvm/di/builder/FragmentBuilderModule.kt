package com.nth.mvvm.di.builder

import com.nth.mvvm.ui.main.song.SongFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by NguyenTienHoa on 3/13/2021
 */

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeSongFragment(): SongFragment


}