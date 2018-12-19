package com.zjy.demo.jetpack

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.zjy.demo.R
import com.zjy.demo.jetpack.ui.viewmodel.ViewModelFragment

class ViewModelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_model_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ViewModelFragment.newInstance())
                .commitNow()
        }
    }

}
