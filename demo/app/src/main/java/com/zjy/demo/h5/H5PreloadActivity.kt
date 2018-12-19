package com.zjy.demo.h5

import android.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.ViewGroup
import com.zjy.demo.R

class H5PreloadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        var h5Root: ConstraintLayout? = null
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h5_preload)

        h5Root = findViewById(R.id.h5_layout)
        h5Root?.addView(WebViewPreLoadHelper.getInstance(this)!!.getWebView(), ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT))
    }
}
