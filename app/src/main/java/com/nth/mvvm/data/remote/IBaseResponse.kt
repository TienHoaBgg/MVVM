package com.nth.mvvm.data.remote

/**
 * Created by NguyenTienHoa on 3/13/2021
 */

interface IBaseResponse {
    fun getErrorCode(): Int
    fun getMsg(): String?
    fun getStatus():Int
}