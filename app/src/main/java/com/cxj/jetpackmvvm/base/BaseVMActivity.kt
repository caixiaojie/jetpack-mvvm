package com.cxj.jetpackmvvm.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.util.DoubleClickExitDetector
import com.cxj.jetpackmvvm.util.WaterMarkUtil
import com.gyf.immersionbar.ImmersionBar
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlin.system.exitProcess

abstract class BaseVMActivity : AppCompatActivity(),RequestLifecycle {
    private lateinit var doubleClickExitDetector: DoubleClickExitDetector

    protected inline fun <reified T : ViewDataBinding> binding(
        @LayoutRes resId: Int,
    ): Lazy<T> = lazy { DataBindingUtil.setContentView<T>(this, resId).apply {
        lifecycleOwner = this@BaseVMActivity
    } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        QMUIStatusBarHelper.translucent(this)
//        QMUIStatusBarHelper.setStatusBarDarkMode(this)

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
    @CallSuper
    override fun startLoading() {
    }
    @CallSuper
    override fun loadFinished() {
    }
    @CallSuper
    override fun loadFailed(msg: String?) {
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