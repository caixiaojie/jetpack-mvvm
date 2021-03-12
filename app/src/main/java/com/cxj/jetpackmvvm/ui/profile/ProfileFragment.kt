package com.cxj.jetpackmvvm.ui.profile

import android.graphics.Color
import android.os.Bundle
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.databinding.FragmentProfileBinding
import com.cxj.jetpackmvvm.model.bean.ProfileItem
import com.cxj.jetpackmvvm.ui.project.ProjectViewModelNew
import com.cxj.reacthttp.base.BaseVMFragment
import com.cxj.reacthttp.dialog.DialogActivity
import com.cxj.reacthttp.dialog.TipsActivity
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.gyf.immersionbar.ImmersionBar
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.zdkj.ktx.ext.startKtxActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.math.abs


/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/8
 *      des:
 * </pre>
 */
class ProfileFragment : BaseVMFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    companion object {
        fun newInstance(): ProfileFragment {
            val args = Bundle()

            val fragment = ProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }
    private val mViewModel by getViewModel(ProjectViewModelNew::class.java)
    private val profileAdapter by lazy { ProfileAdapter() }
    private lateinit var nameArray: Array<String>
    private var profileItemList = ArrayList<ProfileItem>()
    private val imageArray = arrayOf(
        R.drawable.ic_message_black_24dp,
        R.drawable.ic_collections_black_24dp,
        R.drawable.ic_account_blog_black_24dp,
        R.drawable.ic_baseline_history_24,
        R.drawable.ic_bug_report_black_24dp,
        R.drawable.ic_github_black_24dp,
        R.drawable.ic_account_circle_black_24dp
    )


    override fun initView() {
        binding.run {
            adapter = profileAdapter

//            val params = CollapsingToolbarLayout.LayoutParams(CollapsingToolbarLayout.LayoutParams.MATCH_PARENT,
//                QMUIDisplayHelper.getStatusBarHeight(requireContext())+QMUIDisplayHelper.getActionBarHeight(requireContext()))
//            mToolbar.layoutParams = params

            profileAdapter.run {
                setOnItemClickListener { adapter, view, position ->
                    when(position) {
                        0 -> {
                            startKtxActivity<DialogActivity>()
                        }
                        1 -> {
                            startKtxActivity<TipsActivity>()
                        }
                        2 -> {
                            mViewModel.testTokenInvalid()
                        }
                    }
                }
            }

            mAppBar.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset -> //verticalOffset始终为0以下的负数
                val percent = abs(verticalOffset * 1.0f) / appBarLayout.totalScrollRange
//                mTopBar.setBackgroundColor(changeAlpha(Color.WHITE, percent))
//                mTopBar.topBar.titleView?.setTextColor(changeAlpha(Color.WHITE, percent))
            })

//            mCollapsingTopBarLayout.setScrimUpdateListener { animation ->
//                LogUtils.i("scrim: " + animation.animatedValue)
//            }
//
//            mCollapsingTopBarLayout.addOnOffsetUpdateListener { layout, offset, expandFraction ->
//                LogUtils.i("offset = $offset; expandFraction = $expandFraction")
//            }
        }
    }

    override fun initData() {
        if (profileItemList.size == 0) {
            nameArray = arrayOf(
                getString(R.string.mine_points),
                getString(R.string.my_collection),
                getString(R.string.mine_blog),
                getString(R.string.browsing_history),
                getString(R.string.mine_nuggets),
                getString(R.string.github),
                getString(R.string.about_me)
            )
            for (index in nameArray.indices) {
                profileItemList.add(
                    ProfileItem(nameArray[index], imageArray[index])
                )
            }
            profileAdapter.setNewInstance(profileItemList)
        }
    }

    override fun startObserve() {
    }

    /** 根据百分比改变颜色透明度  */
    private fun changeAlpha(color: Int, fraction: Float): Int {
        val red: Int = Color.red(color)
        val green: Int = Color.green(color)
        val blue: Int = Color.blue(color)
        val alpha = (Color.alpha(color) * fraction).toInt()
        return Color.argb(alpha, red, green, blue)
    }

    override fun onResume() {
        super.onResume()
        ImmersionBar.with(this).statusBarDarkFont(true).titleBar(toolbar).init()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            ImmersionBar.with(this).statusBarDarkFont(true).titleBar(toolbar).init()
        }
    }
}