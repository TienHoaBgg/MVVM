package com.nth.mvvm.ui.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.nth.mvvm.R
import com.nth.mvvm.ui.customview.GlideApp

/**
 * Created by NguyenTienHoa on 3/13/2021
 */

class LoadDataBinding {

    companion object {
        @JvmStatic
        @BindingAdapter(value = ["bind:urlImage"], requireAll = false)
        fun AppCompatImageView.setUrlImage(urlImage: String?) {
            if (StringUtils.isNotBlank(urlImage)) {
                GlideApp.with(this)
                    .load(urlImage)
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .into(this)
            }
        }
    }
}