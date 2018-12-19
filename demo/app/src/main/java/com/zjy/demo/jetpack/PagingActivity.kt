package com.zjy.demo.jetpack

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zjy.demo.R
import com.zjy.demo.jetpack.paging.PagingFragment

class PagingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging)
        supportFragmentManager.beginTransaction().replace(R.id.paging_layout, PagingFragment.newInstance())
            .commitNowAllowingStateLoss()
    }
}
