package com.cxj.jetpackmvvm.ui.official

import android.content.Context
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.base.BaseVMFragment
import com.cxj.jetpackmvvm.databinding.FragmentOfficialAccountsBinding
import com.cxj.jetpackmvvm.ui.main.MainActivity
import com.cxj.jetpackmvvm.ui.main.MainViewModel
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.fragment_official_accounts.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/8
 *      des:
 * </pre>
 */
class OfficialAccountsFragment : BaseVMFragment<FragmentOfficialAccountsBinding>(R.layout.fragment_official_accounts) {
    companion object {
        fun newInstance(): OfficialAccountsFragment{
            val args = Bundle()

            val fragment = OfficialAccountsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun startObserve() {
    }

    override fun onResume() {
        super.onResume()
        ImmersionBar.with(this).statusBarDarkFont(false).titleBar(R.id.toolbar).init()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            ImmersionBar.with(this).statusBarDarkFont(false).titleBar(R.id.toolbar).init()
        }
    }
}