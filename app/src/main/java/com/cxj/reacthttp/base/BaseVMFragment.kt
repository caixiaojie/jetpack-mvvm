package com.cxj.reacthttp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.ui.main.MainActivity
import com.cxj.jetpackmvvm.ui.main.MainViewModel
import com.gyf.immersionbar.ImmersionBar
import com.zdkj.reacthttp.base.BaseReactiveFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/3/5
 *      des:
 * </pre>
 */
abstract class BaseVMFragment<T : ViewDataBinding>(@LayoutRes val layoutId: Int) :
    BaseReactiveFragment(layoutId){
    lateinit var binding:T

    protected  fun <T : ViewDataBinding> binding(
        inflater: LayoutInflater,
        @LayoutRes layoutId: Int,
        container: ViewGroup?,
    ): T =   DataBindingUtil.inflate<T>(inflater, layoutId, container, false).apply {
        lifecycleOwner = this@BaseVMFragment
    }


    /**
     * 监听fragment返回键 返回首页
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.onBackPressedDispatcher?.addCallback(this,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                //在基类判断 宿主activity是MainActivity时逻辑
                if (activity is MainActivity) {
                    val currentPage =  (activity as MainActivity).binding.container.mViewModel.getPage() ?: 0
                    if (currentPage != 0) {
                        (activity as MainActivity).binding.container.fragmentManger(0)
                    }
                }

            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = binding(inflater, layoutId, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ImmersionBar.with(this).keyboardEnable(true).init()
        startObserve()
        initView()
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun initView()
    abstract fun initData()
    abstract fun startObserve()

    override fun onVisible() {
        super.onVisible()

        // Called when the fragment is visible.

    }

    override fun onInvisible() {
        super.onInvisible()

        // Called when the Fragment is not visible.
    }

    override fun onVisibleFirst() {
        super.onVisibleFirst()

        // Called when the fragment is visible for the first time.
        // You can load data here for lazy loading.

        initData()
    }

    override fun onVisibleExceptFirst() {
        super.onVisibleExceptFirst()

        // Called when the fragment is visible except first time.
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
        EventBus.getDefault().register(this);
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(event: MessageEvent?) { /* Do something */

    }
}