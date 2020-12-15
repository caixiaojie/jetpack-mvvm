package com.cxj.jetpackmvvm.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/4
 *      des:
 * </pre>
 */
class MainViewModel : ViewModel() {
    private val pageLiveData = MutableLiveData<Int>()

    fun setPage(page: Int) {
        pageLiveData.value = page
    }

    fun getPage():Int? {
        return pageLiveData.value
    }
}