package com.cxj.jetpackmvvm.ui.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.base.BaseVMFragment
import com.cxj.jetpackmvvm.databinding.FragmentProjectv2Binding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ImmersionBar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/8
 *      des:
 * </pre>
 */
class ProjectFragmentV2 : BaseVMFragment<FragmentProjectv2Binding>(R.layout.fragment_projectv2),
    ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {
    private val mViewModel by viewModel<ProjectViewModel>()
//    private val adapter by lazy { FragmentPagerAdapter(this) }

    companion object {
        fun newInstance(): ProjectFragmentV2{
            val args = Bundle()

            val fragment = ProjectFragmentV2()
            fragment.arguments = args
            return fragment
        }
    }
    override fun initView() {
        binding.run {

        }
    }

    override fun initData() {
        mViewModel.getProjectTree()
    }

    override fun startObserve() {
        mViewModel.run {
            uiProjectTreeState.observe(this@ProjectFragmentV2, {
                it.isSuccess?.let { data ->
                    val nameList = mutableListOf<String>()
                    val viewList = mutableListOf<Fragment>()
                    data.forEach { project ->
                        nameList.add(project.name)
                        viewList.add(ProjectListFragment.newInstance(project.id))
                    }
                    binding.mVp.adapter = FragmentPagerAdapter(this@ProjectFragmentV2,viewList)
                    TabLayoutMediator(binding.mTabLayout, binding.mVp
                    ) { tab: TabLayout.Tab, position: Int ->
                        tab.text = nameList[position]
                    }.attach()
                    binding.mTabLayout.addOnTabSelectedListener(this@ProjectFragmentV2)
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