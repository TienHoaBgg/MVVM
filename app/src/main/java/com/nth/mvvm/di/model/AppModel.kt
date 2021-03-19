package com.nth.mvvm.di.model

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.nth.mvvm.data.local.AppDatabase
import com.nth.mvvm.ui.base.viewmodel.ViewModelModule
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

/**
 * Created by NguyenTienHoa on 3/13/2021
 */

@Module(includes = [ViewModelModule::class])
class AppModel {

    @Singleton
    @Provides
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Singleton
    @Provides
    internal fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "hole-database").allowMainThreadQueries().build()
    }


    @Singleton
    @Provides
    internal fun provideSchedule(): Executor {
        return Executors.newFixedThreadPool(4)
    }
}