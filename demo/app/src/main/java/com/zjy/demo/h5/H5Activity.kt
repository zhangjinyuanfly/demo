package com.zjy.demo.h5

import android.app.ActionBar
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.ViewGroup
import com.zjy.demo.R

class H5Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("zjy","H5Activity onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h5)

        // load H5
        WebViewPreLoadHelper.getInstance(this)?.loadUrl("https://www.163.com/")

    }

    override fun onDestroy() {
        super.onDestroy()
        WebViewPreLoadHelper.getInstance(this)!!.clear()
    }
}
