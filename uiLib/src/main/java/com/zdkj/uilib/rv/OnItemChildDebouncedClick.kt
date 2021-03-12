package com.zdkj.uilib.rv

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zdkj.ktx.utils.NoDoubleClickListener

abstract class OnItemChildNoDoubleClickListener: OnItemChildClickListener {

    private var lastClickTime: Long = 0

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > NoDoubleClickListener.MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime
            onNoDoubleClick(adapter, view, position)
        }
    }

    protected abstract fun onNoDoubleClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int)

    companion object {
        var MIN_CLICK_DELAY_TIME                       = 500L
    }
}

fun <T, K : BaseViewHolder> BaseQuickAdapter<T, K>.onItemChildDebouncedClick(click: (adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) -> Unit) {
    setOnItemChildClickListener(object : OnItemChildNoDoubleClickListener(){
        override fun onNoDoubleClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
            click(adapter, view, position)
        }
    })
}
