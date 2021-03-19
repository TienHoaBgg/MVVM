package com.nth.mvvm.di.compoment

import android.app.Application
import android.content.Context
import com.nth.mvvm.common.HoleApplication
import com.nth.mvvm.data.local.AppDatabase
import com.nth.mvvm.di.builder.ActivityBuilderModule
import com.nth.mvvm.di.builder.FragmentBuilderModule
import com.nth.mvvm.di.model.AppModel
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import java.util.concurrent.Executor
import javax.inject.Singleton

/**
 * Created by NguyenTienHoa on 3/13/2021
 */

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModel::class,
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class,
        FragmentBuilderModule::class]
)

interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: HoleApplication)
    fun context(): Context
    fun appDatabase(): AppDatabase
    fun schedule(): Executor

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}