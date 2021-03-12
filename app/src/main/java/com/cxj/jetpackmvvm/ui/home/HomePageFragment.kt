package com.cxj.jetpackmvvm.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.viewpager2.widget.ViewPager2
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.base.BaseVMFragment
import com.cxj.jetpackmvvm.databinding.FragmentHomeBinding
import com.zdkj.ktx.ext.startKtxActivity
import com.zdkj.ktx.ext.toast
import com.cxj.jetpackmvvm.model.bean.BannerBean
import com.cxj.jetpackmvvm.model.pojo.QueryHomeArticle
import com.cxj.jetpackmvvm.ui.article.*
import com.cxj.jetpackmvvm.util.WaterMarkUtil
import com.gyf.immersionbar.ImmersionBar
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/8
 *      des:
 * </pre>
 */
class HomePageFragment : BaseVMFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val rvAdapter by lazy { HomePageAdapter() }
    private val mViewModel by viewModel<HomeViewModel>()
    private var page = 1
    private val queryHomeArticle by lazy { QueryHomeArticle(page,false) }
    private lateinit var mBannerView: BannerViewPager<BannerBean, BannerViewHolder>

    companion object {
        fun newInstance(): HomePageFragment{
            val args = Bundle()

            val fragment = HomePageFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun initView() {
        binding.run {
            WaterMarkUtil.showWatermarkView(requireActivity(), "Hello Word!")
            adapter = rvAdapter
            rvAdapter.headerWithEmptyEnable = true
            mSmartRefresh.setOnRefreshListener {
                page = 1
                queryHomeArticle.page = page
                queryHomeArticle.isRefresh = true
                initData()
            }
            mSmartRefresh.setOnLoadMoreListener {
                page++
                queryHomeArticle.page = page
                queryHomeArticle.isRefresh = true
                initData()
            }
            rvAdapter.setOnItemClickListener { adapter, view, position ->
                val article = rvAdapter.data[position]
                val bundle = bundleOf()
                bundle.putString(PAGE_NAME, article.title)
                bundle.putString(PAGE_URL, article.link)
                bundle.putInt(PAGE_ID, article.id)
                bundle.putInt(IS_COLLECTION, if (article.collect) 1 else 0)
                bundle.putInt(USER_ID, article.userId)
                startKtxActivity<ArticleActivity>(extra = bundle)
            }

            val bannerLayout = LayoutInflater.from(requireContext()).inflate(R.layout.banner_view,null)
            rvAdapter.addHeaderView(bannerLayout)


            setupViewPager(bannerLayout)
        }
    }

    override fun initData() {
        mViewModel.getHomeBanner()
        mViewModel.getHomeArticle(queryHomeArticle)
    }

    override fun startObserve() {
        mViewModel.run {
            uiHomeArticleState.observe(this@HomePageFragment, {
                if (it.isLoading && !it.isRefresh) {//不是手动刷新动作 才展示loading
                    rvAdapter.setEmptyView(getLoadingView())
                }

                it.isSuccess?.let { data->
                    rvAdapter.run {
                        binding.mSmartRefresh.closeHeaderOrFooter()
                        if (queryHomeArticle.page == 1) {
                            setNewInstance(data.toMutableList())
                        }else {
                            addData(data)
                        }
                    }
                }
                it.isError?.let { msg->
                    toast(requireContext(),msg)
                    binding.mSmartRefresh.closeHeaderOrFooter()
                    rvAdapter.setEmptyView(getErrorView())
                }
            })

            uiHomeBannerState.observe(this@HomePageFragment,{
                it.isSuccess?.let { data ->
                    mBannerView.refreshData(data)
                    rvAdapter.notifyDataSetChanged()
                }
            })
        }
    }

    private fun setupViewPager(bannerLayout: View) {
        mBannerView = bannerLayout.findViewById(R.id.mBanner)
        mBannerView.apply {
            adapter = BannerAdapter()
            setAutoPlay(true)
            setLifecycleRegistry(lifecycle)
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            setIndicatorSliderGap(resources.getDimensionPixelOffset(R.dimen.dp_4))
            setIndicatorMargin(0, 0, 0, resources.getDimension(R.dimen.dp_20).toInt())
            setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
            setIndicatorSliderRadius(resources.getDimension(R.dimen.dp_3).toInt(), resources.getDimension(R.dimen.dp_4_5).toInt())
            setIndicatorSliderColor(
                ContextCompat.getColor(requireContext(), R.color.white),
                ContextCompat.getColor(requireContext(), R.color.white_alpha_75))
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {

                }
            })
        }.create()
    }

    override fun onResume() {
        super.onResume()
        ImmersionBar.with(this).statusBarDarkFont(false).titleBar(toolbar).init()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            ImmersionBar.with(this).statusBarDarkFont(false).titleBar(toolbar).init()
        }
    }
}