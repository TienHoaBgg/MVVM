package com.nth.mvvm.ui.utils

import androidx.fragment.app.FragmentManager
import com.nth.mvvm.R
import com.nth.mvvm.ui.base.AnimationScreen
import com.nth.mvvm.ui.base.fragment.BaseFragment
import com.nth.mvvm.ui.main.song.SongFragment

object OpenFragmentUtils {

    @JvmStatic
    fun getAnimationScreenFullOpen(): AnimationScreen {
        return AnimationScreen(R.anim.enter_to_left, R.anim.exit_to_left, R.anim.enter_to_right, R.anim.exit_to_right)
    }

    @JvmStatic
    fun openFirstSongFragment(manager: FragmentManager) {
        val transaction = manager.beginTransaction()
        BaseFragment.openFragment(manager, transaction, SongFragment::class.java, null,
            hasAddbackstack = false,
            hasCommitTransaction = true,
            animations = null,
            fragmentContent = R.id.content
        )
    }


}