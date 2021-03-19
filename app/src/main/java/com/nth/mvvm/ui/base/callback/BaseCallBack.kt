package com.nth.mvvm.ui.base.callback

import com.nth.mvvm.ui.base.BaseViewUI

/**
 * Created by NguyenTienHoa on 3/13/2021
 */

interface BaseCallBack : BaseViewUI {
    fun error(id: String, error: Throwable)
}