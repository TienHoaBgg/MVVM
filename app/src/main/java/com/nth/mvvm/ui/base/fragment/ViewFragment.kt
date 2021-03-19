package com.nth.mvvm.ui.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nth.mvvm.ui.base.BaseViewUI
import com.nth.mvvm.ui.base.activity.BaseActivity

/**
 * Created by NguyenTienHoa on 3/13/2021
 */

interface ViewFragment : BaseViewUI {
    fun onCreateViewControl(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View
    fun onViewCreatedControl(view: View, savedInstanceState: Bundle?)
    fun onDestroyViewControl()
    fun reload(bundle: Bundle)
    fun getBaseActivity(): BaseActivity
}