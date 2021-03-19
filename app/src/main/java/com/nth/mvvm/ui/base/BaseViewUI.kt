package com.nth.mvvm.ui.base

/**
 * Created by NguyenTienHoa on 3/13/2021
 */

interface BaseViewUI {
    fun getLayoutMain(): Int

    fun setEvents()

    fun initComponents()

    fun onBackRoot()

    fun showMessage(message: String)

    fun showMessage(messageId: Int)

    val isDestroyView: Boolean

    fun onResumeControl()

    fun onPauseControl()

    fun hideKeyBoard(): Boolean
}