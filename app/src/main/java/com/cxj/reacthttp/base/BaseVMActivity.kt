package com.cxj.reacthttp.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.util.DoubleClickExitDetector
import com.gyf.immersionbar.ImmersionBar
import com.zdkj.reacthttp.base.BaseReactiveActivity
import kotlin.system.exitProcess

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/3/5
 *      des:
 * </pre>
 */
abstract class BaseVMActivity : BaseReactiveActivity(){
    private lateinit var doubleClickExitDetector: DoubleClickExitDetector

    protected inline fun <reified T : ViewDataBinding> binding(
        @LayoutRes resId: Int,
    ): Lazy<T> = lazy { DataBindingUtil.setContentView<T>(this, resId).apply {
        lifecycleOwner = this@BaseVMActivity
    } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        doubleClickExitDetector = DoubleClickExitDetector(this, "再按一次退出", 2000)
        startObserve()
        initView()
        initData()
    }

    override fun onResume() {
        super.onResume()
        initImmersionBar()
    }

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    protected open fun initImmersionBar() {
        //设置共同沉浸式样式
        ImmersionBar.with(this).navigationBarColor(R.color.colorPrimary).init()
    }

    abstract fun initView()
    abstract fun initData()
    abstract fun startObserve()

    open fun isDoubleClickExit(): Boolean {
        return false
    }

    override fun onBackPressed() {
        if (isDoubleClickExit()) {
            val isExit = doubleClickExitDetector.click()
            if (isExit) {
                super.onBackPressed()
                exitProcess(0)
            }
        } else {
            super.onBackPressed()
        }
    }


    open fun getEmptyView(): Int {
        return R.layout.empty_view
    }
    open fun getErrorView(): Int {
        return R.layout.error_view
    }
    open fun getLoadingView(): Int {
        return R.layout.loading_view
    }
}