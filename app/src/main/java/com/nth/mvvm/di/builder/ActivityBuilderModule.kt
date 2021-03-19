package com.nth.mvvm.di.builder

import com.nth.mvvm.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by NguyenTienHoa on 3/13/2021
 */

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity

}