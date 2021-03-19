package com.nth.mvvm.common

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.nth.mvvm.di.compoment.AppComponent
import com.nth.mvvm.di.compoment.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by NguyenTienHoa on 3/13/2021
 */

class HoleApplication : MultiDexApplication(), HasActivityInjector, HasSupportFragmentInjector {
    lateinit var appComponent: AppComponent

    @Inject
    internal lateinit var activityDispatchingInjector: DispatchingAndroidInjector<Activity>
    @Inject
    internal lateinit var fragmentyDispatchingInjector: DispatchingAndroidInjector<Fragment>

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object{
        private var app: HoleApplication? = null
        @JvmStatic
        fun getInstance(): HoleApplication {
            if (app == null) {
                app = HoleApplication()
            }
            return app!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
    }

    fun appDatabase() = appComponent.appDatabase()
    fun schedule() = appComponent.schedule()

    override fun activityInjector(): AndroidInjector<Activity?> {
        return activityDispatchingInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentyDispatchingInjector
    }
}