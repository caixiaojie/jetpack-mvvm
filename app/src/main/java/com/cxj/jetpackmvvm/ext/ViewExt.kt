package com.cxj.jetpackmvvm.ext

import android.view.View

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/14
 *      des:
 * </pre>
 */
inline fun View.setSafeListener(crossinline action: () -> Unit) {
    var lastClick = 0L
    setOnClickListener {
        val gap = System.currentTimeMillis() - lastClick
        lastClick = System.currentTimeMillis()
        if (gap < 1500) return@setOnClickListener
        action.invoke()
    }
}