package com.zjy.demo.jetpack.paging

import android.arch.paging.DataSource

/**
 * 自定义Factory 创建PageList使用
 */
class PageDataSourceFactory(val dataRepository: DataRepository): DataSource.Factory<Int, DataBean>() {

    override fun create(): DataSource<Int, DataBean> {
        return PageDataSource(dataRepository)
    }

}