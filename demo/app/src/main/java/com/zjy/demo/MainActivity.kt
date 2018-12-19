package com.zjy.demo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zjy.demo.annotation.AnnotationActivity
import com.zjy.demo.h5.H5Activity
import com.zjy.demo.jetpack.PagingActivity
import com.zjy.demo.jetpack.ViewModelActivity

class MainActivity : AppCompatActivity() {

    var demoList: ArrayList<DemoData> = ArrayList()

    var recycleDemo: RecyclerView? = null
    var demoHolder: DemoHolder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        recycleDemo = findViewById(R.id.recycle_demo)
        recycleDemo?.layoutManager = LinearLayoutManager(this)
        recycleDemo?.adapter = DemoAdapter()
    }


    inner class DemoAdapter: RecyclerView.Adapter<DemoHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DemoHolder {
            var holder:DemoHolder? = null
            val inflate = LayoutInflater.from(this@MainActivity).inflate(R.layout.demo_item, p0, false);
            holder = DemoHolder(inflate);
            holder.nameView = inflate.findViewById(R.id.name)
            return holder
        }

        override fun getItemCount(): Int {
            return demoList.size
        }

        override fun onBindViewHolder(holder: DemoHolder, p1: Int) {
            var dataMap = demoList.get(p1)
            holder.setValue(dataMap)
        }
    }

    inner class DemoHolder(itemView :View): RecyclerView.ViewHolder(itemView) {
        var nameView: TextView? = null
        fun setValue(data: DemoData) {
            itemView.tag = data
            nameView?.text = data.name
            itemView.setOnClickListener(clickListener)
        }
    }

    private var clickListener = View.OnClickListener {
        var data:DemoData = it.tag as DemoData
        var intent = Intent(this@MainActivity, data.jumpActivity)
        startActivity(intent)
    }

    private fun initData() {
        var viewModelData = DemoData()
        viewModelData.name = "ViewModel"
        viewModelData.jumpActivity = ViewModelActivity::class.java
        demoList.add(viewModelData)

        var pagingData = DemoData()
        pagingData.name = "Paging"
        pagingData.jumpActivity = PagingActivity::class.java
        demoList.add(pagingData)

        var h5Data = DemoData()
        h5Data.name = "H5"
        h5Data.jumpActivity = H5Activity::class.java
        demoList.add(h5Data)

        var annotation = DemoData()
        annotation.name = "annotation"
        annotation.jumpActivity = AnnotationActivity::class.java
        demoList.add(annotation)

    }

}
