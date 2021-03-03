package com.cxj.jetpackmvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.cxj.jetpackmvvm.R
import com.gyf.immersionbar.ImmersionBar

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/3/3
 *      des: 懒加载fragment
 * </pre>
 */
abstract class BaseVMLazyFragment<T : ViewDataBinding>(@LayoutRes val layoutId: Int) :
    VisibilityFragment(layoutId) {
    lateinit var binding:T

    protected  fun <T : ViewDataBinding> binding(
        inflater: LayoutInflater,
        @LayoutRes layoutId: Int,
        container: ViewGroup?,
    ): T =   DataBindingUtil.inflate<T>(inflater, layoutId, container, false).apply {
        lifecycleOwner = this@BaseVMLazyFragment
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
}