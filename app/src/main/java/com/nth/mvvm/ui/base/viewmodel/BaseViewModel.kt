package com.nth.mvvm.ui.base.viewmodel

import android.annotation.SuppressLint
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.nth.mvvm.common.Constants
import com.nth.mvvm.data.local.AppDatabase
import com.nth.mvvm.data.remote.IBaseResponse
import com.nth.mvvm.ui.base.callback.BaseCallBack
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference
import java.util.concurrent.Executor

/**
 * Created by NguyenTienHoa on 3/13/2021
 */

abstract class BaseViewModel<CallBack : BaseCallBack> : ViewModel {
    protected val appDatabase: AppDatabase
    protected val schedulers: Executor
    var callBack: WeakReference<CallBack>? = null
    protected val mDiableAll: CompositeDisposable
    protected val mIsLoading = ObservableBoolean(false)
    private var mIsDestroy: Boolean

    constructor(appDatabase: AppDatabase,
                schedulers: Executor
    ) {
        this.appDatabase = appDatabase
        this.schedulers = schedulers
        this.mDiableAll = CompositeDisposable()
        mIsDestroy = false

    }

    override fun onCleared() {
        mDiableAll.dispose()
        super.onCleared()
    }

    fun isLoading() = mIsLoading
    fun setIsLoading(isLoading: Boolean) {
        mIsLoading.set(isLoading)
    }

    fun getCallBack() : CallBack? {
        return callBack?.get()
    }

    companion object {
        fun checkExpire(data: IBaseResponse): Boolean {
            return data.getErrorCode() == Constants.CODE_TOKEN_EXPIRE || data.getStatus() == Constants.CODE_TOKEN_EXPIRE
        }

        fun checkExpire(data: Throwable): Boolean {
            if (data is retrofit2.HttpException) {
                if (data.code() == Constants.CODE_TOKEN_EXPIRE) {
                    return true
                }
            }
            return false
        }
    }

    protected fun <T : IBaseResponse> subscribeHasDispose(observable: Observable<T>?, onNext: (T) -> Unit, onError: (Throwable) -> Unit) {
        if (observable == null) {
            return
        }
        mDiableAll.add(observable.subscribe(
            {
                if (mIsDestroy) {
                    return@subscribe
                }

                onNext(it)
            },
            {
                run {
                    if (Constants.DEBUG) {
                        it.printStackTrace()
                    }
                    if (mIsDestroy) {
                        return@run
                    }
                }
                onError(it)
            }))
    }

    protected fun <T : MutableList<out IBaseResponse>> subscribeListHasDispose(observable: Observable<T>?, onNext: (T) -> Unit, onError: (Throwable) -> Unit) {
        if (observable == null) {
            return
        }
        mDiableAll.add(observable.subscribe(
            {
                if (mIsDestroy) {
                    return@subscribe
                }
                onNext(it)
            },
            {
                run {
                    if (Constants.DEBUG) {
                        it.printStackTrace()
                    }
                    if (mIsDestroy) {
                        return@run
                    }
                    onError(it)
                }
            }))
    }

    protected fun <T> subscribeListHasResultDispose(observable: Observable<T>?, onNext: (T) -> Unit, onError: (Throwable) -> Unit): Disposable? {
        if (observable == null) {
            return null
        }
        return observable.subscribe(
            {
                if (mIsDestroy) {
                    return@subscribe
                }
                onNext(it)
            },
            {
                run {
                    if (Constants.DEBUG) {
                        it.printStackTrace()
                    }
                    if (mIsDestroy) {
                        return@run;
                    }
                    onError(it)
                }
            })
    }


    @SuppressLint("CheckResult")
    protected fun <T> subscribeNotDispose(observable: Observable<T>?, onNext: (T) -> Unit, onError: (Throwable) -> Unit) {
        if (observable == null) {
            return
        }
        observable.subscribe(
            {
                if (mIsDestroy) {
                    return@subscribe
                }
                onNext(it)
            },
            {
                run {
                    if (Constants.DEBUG) {
                        it.printStackTrace();
                    }
                    if (mIsDestroy) {
                        return@run;
                    }
                    onError(it)
                }
            })
    }


    protected fun <T> subscribeHasResultDispose(observable: Observable<T>?, onNext: (T) -> Unit, onError: (Throwable) -> Unit): Disposable? {
        if (observable == null) {
            return null
        }
        return observable.subscribe(
            {
                if (mIsDestroy) {
                    return@subscribe
                }
                onNext(it)
            },
            {
                run {
                    if (Constants.DEBUG) {
                        it.printStackTrace()
                    }
                    if (mIsDestroy) {
                        return@run
                    }
                    onError(it)
                }
            })
    }

    protected fun makeFunOnOtherThread(method: () -> Unit) {
        Observable.create((ObservableOnSubscribe<Boolean> {
            method()
            it.onNext(true)
            it.onComplete()
        }))
            .subscribeOn(Schedulers.from(schedulers))
            .subscribe()
    }

}