package com.cxj.jetpackmvvm.ui.project

import android.content.res.Configuration
import android.os.Bundle
import androidx.core.os.bundleOf
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.databinding.FragmentProjectListBinding
import com.zdkj.ktx.ext.startKtxActivity
import com.cxj.jetpackmvvm.model.pojo.QueryArticle
import com.cxj.jetpackmvvm.ui.article.*
import com.cxj.jetpackmvvm.ui.home.HomePageAdapter
import com.cxj.reacthttp.base.BaseVMFragment

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/15
 *      des:
 * </pre>
 */
class ProjectListFragmentNew : BaseVMFragment<FragmentProjectListBinding>(R.layout.fragment_project_list) {
    private var page = 1
    private var cid = 0
    private val queryArticle by lazy { QueryArticle(page,cid,false) }
    private val mViewModel by getViewModel(ProjectViewModelNew::class.java)
    private val mAdapter by lazy { HomePageAdapter() }
    companion object {
        private const val PROJECT_CID = "PROJECT_CID"
        fun newInstance(cid: Int): ProjectListFragmentNew{
            val args = Bundle()
            args.putInt(PROJECT_CID,cid)
            val fragment = ProjectListFragmentNew()
            fragment.arguments = args
            return fragment
        }
    }
    override fun initView() {
        binding.run { 
            arguments?.run {
                cid = getInt(PROJECT_CID)
            }

            mToTopRv.setRecyclerViewLayoutManager(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            mToTopRv.setAdapter(mAdapter)
            mToTopRv.onRefreshListener({
                page = 1
                queryArticle.isRefresh = true
                queryArticle.page = page
                initData()
            }, {
                page++
                queryArticle.page = page
                queryArticle.isRefresh = true
                initData()
            })

            mAdapter.run {
                setOnItemClickListener { adapter, view, position ->
                    val article = mAdapter.data[position]
                    val bundle = bundleOf()
                    bundle.putString(PAGE_NAME, article.title)
                    bundle.putString(PAGE_URL, article.link)
                    bundle.putInt(PAGE_ID, article.id)
                    bundle.putInt(IS_COLLECTION, if (article.collect) 1 else 0)
                    bundle.putInt(USER_ID, article.userId)
                    startKtxActivity<ArticleActivity>(extra = bundle)
                }
            }
        }
    }

    override fun initData() {
        mViewModel.getProjectArticle(queryArticle)
    }

    override fun startObserve() {
        mViewModel.run {
            projectArticleData.observe(lLifecycleOwner, {
                mAdapter.run {
                    if (queryArticle.page == 1) {
                        setNewInstance(it.toMutableList())
                    }else {
                        addData(it)
                    }
                }
            })
        }
    }
}