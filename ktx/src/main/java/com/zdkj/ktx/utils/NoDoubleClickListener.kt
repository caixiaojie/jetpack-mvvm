package com.zdkj.ktx.utils

import android.view.View

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/3/9
 *      des:
 * </pre>
 */
abstract class NoDoubleClickListener : View.OnClickListener {

    private var lastClickTime: Long = 0

    override fun onClick(v: View) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime
            onNoDoubleClick(v)
        }
    }

    protected abstract fun onNoDoubleClick(v: View)

    companion object {
        var MIN_CLICK_DELAY_TIME                       = 500L
    }
}

/** Add function to View to avoid click twice */
fun View.onDebouncedClick(click: (view: View) -> Unit) {
    setOnClickListener(object : NoDoubleClickListener() {
        override fun onNoDoubleClick(v: View) {
            click(v)
        }
    })
}