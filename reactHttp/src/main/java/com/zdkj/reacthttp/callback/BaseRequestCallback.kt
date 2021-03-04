package com.zdkj.reacthttp.callback

import android.widget.Toast
import com.zdkj.reacthttp.exception.BaseHttpException

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/3/4
 *      des:
 * </pre>
 */
open class BaseRequestCallback(
    internal var onStart: (() -> Unit)? = null,
    internal var onCancelled: (() -> Unit)? = null,
    internal var onFailed: ((BaseHttpException) -> Unit)? = null,
    internal var onFailToast: (() -> Boolean) = {true},
    internal var onFinally: (() -> Unit)? = null
) {

    fun onStart(block: () -> Unit) {
        this.onStart = block
    }
    /**
     * 如果外部主动取消了网络请求，不会回调 onFail，而是回调此方法，随后回调 onFinally
     * 但如果当取消网络请求时已回调了 onSuccess / onSuccessIO 方法，则不会回调此方法
     */
    fun onCancelled(block: () -> Unit) {
        this.onCancelled = block
    }

    /**
     * 当网络请求失败时会调用此方法，在 onFinally 被调用之前执行
     */
    fun onFailed(block: (BaseHttpException) -> Unit) {
        this.onFailed = block
    }

    /**
     * 用于控制是否当网络请求失败时 Toast 失败原因
     * 默认为 true，即进行 Toast 提示
     */
    fun onFailToast(block: () -> Boolean) {
        this.onFailToast = block
    }

    /**
     * 在网络请求结束之后（不管请求成功与否）且隐藏 Loading 之前执行
     */
    fun onFinally(block: () -> Unit) {
        this.onFinally = block
    }

}