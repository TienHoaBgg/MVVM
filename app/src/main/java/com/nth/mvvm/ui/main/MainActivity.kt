package com.nth.mvvm.ui.main

import android.os.Bundle
import com.nth.mvvm.R
import com.nth.mvvm.ui.base.activity.BaseActivity
import com.nth.mvvm.ui.utils.OpenFragmentUtils

/**
 * Created by NguyenTienHoa on 3/13/2021
 */

class MainActivity : BaseActivity() {

    override fun getLayoutMain() = R.layout.activity_main

    override fun setEvents() {

    }

    override fun onCreateControl(savedInstanceState: Bundle?) {
        super.onCreateControl(savedInstanceState)

    }

    override fun initComponents() {
        OpenFragmentUtils.openFirstSongFragment(supportFragmentManager)
    }
}