package com.nth.mvvm.ui.main.song

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nth.mvvm.R
import com.nth.mvvm.data.model.ItemSong
import com.nth.mvvm.databinding.FragmentSongBinding
import com.nth.mvvm.ui.base.fragment.BaseHoleFragment
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_song.*

/**
 * Created by NguyenTienHoa on 3/13/2021
 */

class SongFragment : BaseHoleFragment<SongCallBack, SongModel>(), SongCallBack, SongAdapter.ISongAdapter, View.OnClickListener {

    private var dis: Disposable? = null

    override fun getViewModel(): Class<SongModel> {
        return SongModel::class.java
    }

    override fun getLayoutMain() = R.layout.fragment_song

    override fun setEvents() {
        getBindingData().btnSearch.setOnClickListener(this)
    }

    override fun initComponents() {
        getBindingData().viewModel = mModel
        mModel.obSong.observe(this, Observer<MutableList<ItemSong>> {
            if (getBindingData().rcSearch.adapter == null) {
                getBindingData().rcSearch.layoutManager = LinearLayoutManager(getBaseActivity())
                getBindingData().rcSearch.adapter = SongAdapter(this@SongFragment)
            } else {
                getBindingData().rcSearch.adapter!!.notifyDataSetChanged()
            }
        })
    }

    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
        when (id) {
            ItemSong::class.java.name -> {
                mModel.getAllSongOffline()
            }
        }
    }

    private fun getBindingData() = mBinding as FragmentSongBinding

    override fun count(): Int {
        if (mModel.obSong.value == null) {
            return 0
        }
        return mModel.obSong.value!!.size
    }

    override fun getData(position: Int) = mModel.obSong.value!![position]

    override fun onClick(view: View) {
        dis?.dispose()
       when(view){
           btn_search -> {
               dis = mModel.getSongs(getBindingData().edtSearch.text.toString())
           }
       }

    }

    override fun onDestroyViewControl() {
        dis?.dispose()
        super.onDestroyViewControl()
    }
}