package com.zjy.demo.jetpack.ui.viewmodel

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.zjy.demo.R

class ViewModelFragment : Fragment() {

    companion object {
        fun newInstance() = ViewModelFragment()
    }

    private lateinit var viewModel: ViewModelViewModel

    var message: TextView? = null
    var button: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val inflate = inflater.inflate(R.layout.view_model_fragment, container, false)
        message = inflate.findViewById(R.id.message)
        button = inflate.findViewById(R.id.button)
        button?.setOnClickListener {
            viewModel.data?.value = "fff"
//            Log.e("zjy","viewModel.data = "+viewModel.data)
//            viewModel.title = "TITLE"
//            message?.text = viewModel.title
        }
        return inflate
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ViewModelViewModel::class.java)
        // oncreate 每次都会调用，viewmodel数据持久化
        message?.text = viewModel.title
        viewModel.data?.observe(this@ViewModelFragment, Observer {
            message?.text = it
        })

        // 注册observer，当viewModel.data发生变化，此处observer也会执行
        viewModel.liveMapData?.observe(this@ViewModelFragment, Observer {
            Log.e("zjy", "liveMapData = $it")
        })
        viewModel.liveSwitchData?.observe(this@ViewModelFragment, Observer {
            Log.e("zjy", "liveSwitchData  = $it")
        })
    }

}
