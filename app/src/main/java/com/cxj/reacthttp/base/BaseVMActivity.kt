package com.cxj.reacthttp.base

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.cxj.jetpackmvvm.R
import com.zdkj.ktx.ext.startKtxActivity
import com.cxj.jetpackmvvm.ui.login.LoginActivity
import com.cxj.jetpackmvvm.util.DoubleClickExitDetector
import com.cxj.reacthttp.event.TokenInvalidEvent
import com.gyf.immersionbar.ImmersionBar
import com.zdkj.reacthttp.base.BaseReactiveActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(event: MessageEvent?) { /* Do something */
        event?.let {
            when(it) {
                is TokenInvalidEvent -> {
                    val flag = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startKtxActivity<LoginActivity>(flags = flag)
                }
            }
        }
    }
}