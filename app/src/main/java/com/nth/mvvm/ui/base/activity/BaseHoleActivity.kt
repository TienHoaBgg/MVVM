package com.nth.mvvm.ui.base.activity

import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.nth.mvvm.BR
import com.nth.mvvm.common.Constants
import com.nth.mvvm.ui.base.callback.BaseCallBack
import com.nth.mvvm.ui.base.viewmodel.BaseViewModel
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import java.util.*
import javax.inject.Inject

/**
 * Created by NguyenTienHoa on 3/13/2021
 */

abstract class BaseHoleActivity <
CallBack : BaseCallBack,
Model : BaseViewModel<CallBack>> : BaseActivity(), HasSupportFragmentInjector {
    @Inject
    internal lateinit var fragmentAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var mModel: Model
    protected var mFirstLoad: Long = 0

    protected abstract fun getViewModel(): Class<Model>

    override fun onCreateControl(savedInstanceState: Bundle?) {
        if (!mIsClearMemoryActivity) {
            mFirstLoad = Date().time
            AndroidInjection.inject(this)
            mBinding = DataBindingUtil.setContentView(this, getLayoutMain())
            mModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel())
            performDataBinding()
            setEvents()
            initComponents()
        }
        super.onCreateControl(savedInstanceState)
    }

    fun getBindingVariable() = BR.viewModel

    private fun performDataBinding() {
        mBinding.setVariable(getBindingVariable(), mModel)
        mBinding.executePendingBindings()
    }

    protected fun <T> finishLoad(t: T, action: (T) -> Unit) {
        if (mIsDestroyView) {
            return
        }
        if (mFirstLoad == (-1).toLong()) {
            action(t)
        } else {
            val currentTime = Date().time
            if (currentTime - mFirstLoad >= Constants.DURATION_ANIMATION) {
                action(t)
            } else {
                Handler().postDelayed({
                    if (mIsDestroyView) {
                        return@postDelayed
                    }
                    action(t)
                }, Constants.DURATION_ANIMATION - (currentTime - mFirstLoad))
            }
            mFirstLoad = -1
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentAndroidInjector
    }
}