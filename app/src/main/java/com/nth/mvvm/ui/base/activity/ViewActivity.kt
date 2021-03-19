package com.nth.mvvm.ui.base.activity

import android.os.Bundle
import android.view.View
import com.nth.mvvm.ui.base.BaseViewUI
import com.nth.mvvm.ui.base.fragment.BaseFragment

/**
 * Created by NguyenTienHoa on 3/13/2021
 */

interface ViewActivity : BaseViewUI {
    fun onCreateControl(savedInstanceState: Bundle?)
    fun onDestroyControl()
    fun findFragmentByTag(tag: String): BaseFragment
    fun setViewRoot(viewRoot: View)
    fun onBackParent()
    fun onStartControl()
    fun onStopControl()
}