package com.zjy.demo.jetpack.paging

import android.arch.lifecycle.Observer
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.arch.paging.PagedListAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zjy.demo.R

class PagingFragment: Fragment() {

    companion object {
        fun newInstance() = PagingFragment()
    }

    private var recyclerView:RecyclerView? = null
    private var adapter:PagingAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.paging_layout, container, false)
        recyclerView = inflate.findViewById(R.id.paging_recycle_id)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        adapter = PagingAdapter()
        recyclerView?.adapter = adapter
        return inflate
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 创建一个PageList的LiveData
        val build = LivePagedListBuilder(
            PageDataSourceFactory(DataRepository()), PagedList.Config.Builder()
                .setPageSize(10) // 分页每次加载多少数据
                .setInitialLoadSizeHint(10) // 首次加载多少数据
                .setEnablePlaceholders(false)
                .build()
        ).build()

        // 注册LiveData observer，注意仅第一次加载的时候调用
        // 把这个PageList设置到PagedListAdapter中。
        // 接下来分页加载就都PageList内部解决了
        build.observe(this@PagingFragment, Observer {
            adapter?.submitList(it)
        })
    }

    /**
     * 数据diffutil 规则，PagedListAdapter初始化使用
     */
    val pagingDiff = object: DiffUtil.ItemCallback<DataBean>() {
        override fun areContentsTheSame(p0: DataBean, p1: DataBean): Boolean {
            return p0 == p1
        }

        override fun areItemsTheSame(p0: DataBean, p1: DataBean): Boolean {
            return p0.id == p1.id
        }
    }

    /**
     * 必须使用这个adapter绑定到recycleview上
     */
    inner class PagingAdapter:PagedListAdapter<DataBean, PagingHolder>(pagingDiff) {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PagingHolder {
            val inflate = layoutInflater.inflate(R.layout.paging_recycle_item, p0, false);
            var holder = PagingHolder(inflate)
            holder.textView = inflate.findViewById(R.id.paging_item_name)
            return holder
        }

        override fun onBindViewHolder(holder: PagingHolder, p1: Int) {
            holder.setValue(getItem(p1))
        }

    }

    inner class PagingHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var textView: TextView? = null
        fun setValue(bean:DataBean?) {
            bean?.let {
                textView?.text = it.name
            }
        }
    }
}