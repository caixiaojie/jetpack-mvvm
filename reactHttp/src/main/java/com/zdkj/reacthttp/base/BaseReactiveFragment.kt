package com.zdkj.reacthttp.base

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.zdkj.reacthttp.viewmodel.IUIActionEventObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/3/5
 *      des:
 * </pre>
 */
open class BaseReactiveFragment(@LayoutRes contentLayoutId: Int = 0) : VisibilityFragment(contentLayoutId),
    IUIActionEventObserver {
    override val lifecycleSupportedScope: CoroutineScope
        get() = lifecycleScope

    override val lContext: Context?
        get() = context

    override val lLifecycleOwner: LifecycleOwner
        get() = this

    private var loadDialog: ProgressDialog? = null

    override fun showLoading(job: Job?) {
        dismissLoading()
        loadDialog = ProgressDialog(lContext).apply {
            setCancelable(true)
            setCanceledOnTouchOutside(false)
            //用于实现当弹窗销毁的时候同时取消网络请求
//            setOnDismissListener {
//                job?.cancel()
//            }
            show()
        }
    }

    override fun dismissLoading() {
        //如果只有一个if分支的时候可以使用takeIf
        //如果 判断里面为true 则返回对象本身  否则返回null
        //结果要用?判空（因为可能返回空）
        loadDialog?.takeIf { it.isShowing }?.dismiss()
        loadDialog = null
    }

    override fun showToast(msg: String) {
        if (msg.isNotBlank()) {
            Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun finishView() {

    }

    override fun onDestroy() {
        super.onDestroy()
        dismissLoading()
    }
}