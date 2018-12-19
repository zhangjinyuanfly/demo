package com.zjy.demo.jetpack.paging

/**
 * 业务数据结构
 */
data class DataBean (
    var id: Long,
    var name: String
)

/**
 * 模拟的一个本地数据库，正常会从服务端请求数据
 */
class DataRepository {

    var data:ArrayList<DataBean> = ArrayList()
    init {
        for(i in 0..100) {
            data.add(DataBean(i.toLong(), "我是$i"))
        }
    }

    fun loadData(size: Int): List<DataBean> {
        return data.subList(0,size)
    }

    fun loadData(index:Int, size: Int):List<DataBean>? {
        if(index < 1 || index >= data.size) {
            return null
        }
        if(index+size >= data.size) {
            return data.subList(index+1, data.size)
        }
        return data.subList(index+1, index+size)
    }

    fun loadPageData(page: Int, size: Int): List<DataBean>? {
        val totalPage = if (data.size % size == 0) {
            data.size / size
        } else {
            data.size / size + 1
        }

        if (page > totalPage || page < 1) {
            return null
        }

        if (page == totalPage) {
            return data.subList((page - 1) * size, data.size)
        }
        return data.subList((page - 1) * size, page * size)
    }

}