package com.zjy.demo.jetpack.paging

import android.arch.paging.PageKeyedDataSource
import android.util.Log

/**
 * paging 中加载数据管理类，这个类会和PageList绑定在一起
 */
class PageDataSource(val dataRepository: DataRepository): PageKeyedDataSource<Int, DataBean>() {

    /**
     * 初始化加载数据
     */
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, DataBean>) {
        val loadData = dataRepository.loadData(params.requestedLoadSize)
        callback.onResult(loadData, null, 2)
        Log.e("zjy","loadInitial = ${params.requestedLoadSize}")
    }

    /**
     * 往后分页加载
     */
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DataBean>) {
        val loadData = dataRepository.loadPageData(params.key, params.requestedLoadSize)
        loadData?.let {
            callback.onResult(loadData, params.key + 1)
        Log.e("zjy", "loadAfter = ${params.key} - ${params.requestedLoadSize}")
        }
    }

    /**
     * 往前分页加载，感觉一般用不到
     */
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DataBean>) {
        val loadData = dataRepository.loadPageData(params.key, params.requestedLoadSize)
        loadData?.let {
            callback.onResult(loadData, params.key - 1)
            Log.e("zjy", "loadBefore = ${params.key} - ${params.requestedLoadSize}")
        }
    }

}