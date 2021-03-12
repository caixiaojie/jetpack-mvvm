package com.zdkj.uilib.rv.listener

import androidx.recyclerview.widget.RecyclerView
import com.zdkj.ktx.ext.logd


/**
 * 自定义的 Recycler 滚动监听，用来处理 FAB 等的隐藏和显示事件
 */
abstract class CustomRecyclerScrollViewListener : RecyclerView.OnScrollListener() {

    private var scrollDist = 0

    private var isVisible = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (isVisible && scrollDist > rvScrollJudgeHeight) {
            ("CRVScrollViewListener Hide").logd()
            hide()
            scrollDist = 0
            isVisible = false
        } else if (!isVisible && scrollDist < -rvScrollJudgeHeight) {
            ("CRVScrollViewListener Show").logd()
            show()
            scrollDist = 0
            isVisible = true
        }
        if (isVisible && dy > 0 || !isVisible && dy < 0) {
            ("CRVScrollViewListener Add Up").logd()
            scrollDist += dy
        }
    }

    abstract fun show()

    abstract fun hide()
    
    companion object {
        /**
         * RecyclerView 判定为上下滚动的距离
         */
        var rvScrollJudgeHeight                     = 20
    }
}
