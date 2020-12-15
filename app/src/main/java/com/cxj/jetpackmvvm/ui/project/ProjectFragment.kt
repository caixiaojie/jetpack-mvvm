package com.cxj.jetpackmvvm.ui.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.base.BaseVMFragment
import com.cxj.jetpackmvvm.databinding.FragmentProjectBinding
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ImmersionBar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/8
 *      des:
 * </pre>
 */
class ProjectFragment : BaseVMFragment<FragmentProjectBinding>(R.layout.fragment_project),
    ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {
    private val mViewModel by viewModel<ProjectViewModel>()
    private val adapter by lazy { FragmentAdapter(activity?.supportFragmentManager) }

    companion object {
        fun newInstance(): ProjectFragment{
            val args = Bundle()

            val fragment = ProjectFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun initView() {
        binding.run {
            mVp.adapter = adapter
            mTabLayout.setupWithViewPager(mVp)
            mVp.addOnPageChangeListener(this@ProjectFragment)
            mTabLayout.addOnTabSelectedListener(this@ProjectFragment)
        }
    }

    override fun initData() {
        mViewModel.getProjectTree()
    }

    override fun startObserve() {
        mViewModel.run {
            uiProjectTreeState.observe(this@ProjectFragment, {
                it.isSuccess?.let { data ->
                    val nameList = mutableListOf<String>()
                    val viewList = mutableListOf<Fragment>()
                    data.forEach { project ->
                        nameList.add(project.name)
                        viewList.add(ProjectListFragment.newInstance(project.id))
                    }
                    adapter.reset(nameList.toTypedArray())
                    adapter.reset(viewList)
                    adapter.notifyDataSetChanged()
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        ImmersionBar.with(this).statusBarDarkFont(false).titleBar(R.id.mTabLayout).init()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            ImmersionBar.with(this).statusBarDarkFont(false).titleBar(R.id.mTabLayout).init()
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }
}