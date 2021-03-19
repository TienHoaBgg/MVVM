package com.nth.mvvm.ui.utils

object StringUtils {
    fun isNotBlank(vararg contents: String?) : Boolean{
        for (content in contents){
            if (content == null || content == ""){
                return false
            }
        }
        return true
    }
}