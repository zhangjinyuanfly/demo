package com.zjy.demo.jetpack.ui.viewmodel

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.util.Log

class ViewModelViewModel : ViewModel() {
    var title: String = "empty"
    var data: MutableLiveData<String>? = null
        get() {
            // 初始化data，和field是同一个对象
            if (field == null) {
                field = MutableLiveData()
            }
            Log.e("zjy", "field = " + field)
            return field
        }

    // data:MutableLiveData 的任何改变也会影响到下面两个data的改变，也会通知上层注册的Observer回调。
    // map返回的是map<X, Y>Y类型
    var liveMapData: LiveData<Int> = Transformations.map<String, Int>(data!!) { input: String? ->
        11
    }
    // map返回的是switchMap<X, Y>的LiveData<Y>类型
    var liveSwitchData: LiveData<String> = Transformations.switchMap<String, String>(data!!) { input: String ->
        MutableLiveData<String>().also { it.value = "222" }
    }
}
