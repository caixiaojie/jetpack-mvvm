package com.cxj.jetpackmvvm.ui.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.databinding.FragmentProjectv2Binding
import com.cxj.reacthttp.base.BaseVMFragment
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
class ProjectFragmentV2New : BaseVMFragment<FragmentProjectv2Binding>(R.layout.fragment_projectv2),
    ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {
    private val mViewModel by getViewModel(ProjectViewModelNew::class.java)

    companion object {
        fun newInstance(): ProjectFragmentV2New{
            val args = Bundle()

            val fragment = ProjectFragmentV2New()
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
            projectTreeData.observe(lLifecycleOwner, {
                val nameList = mutableListOf<String>()
                val viewList = mutableListOf<Fragment>()
                it.forEach { project ->
                    nameList.add(project.name)
                    viewList.add(ProjectListFragmentNew.newInstance(project.id))
                }
                binding.mVp.adapter = FragmentPagerAdapter(this@ProjectFragmentV2New,viewList)
                TabLayoutMediator(binding.mTabLayout, binding.mVp
                ) { tab: TabLayout.Tab, position: Int ->
                    tab.text = nameList[position]
                }.attach()
                binding.mTabLayout.addOnTabSelectedListener(this@ProjectFragmentV2New)
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