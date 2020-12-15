package com.cxj.jetpackmvvm.base

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/8
 *      des:
 * </pre>
 */
interface RequestLifecycle {
    fun startLoading()

    fun loadFinished()

    fun loadFailed(msg: String?)
}