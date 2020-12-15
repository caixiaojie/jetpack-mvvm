package com.cxj.jetpackmvvm.ui.project

import android.content.res.Configuration
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.base.BaseVMFragment
import com.cxj.jetpackmvvm.databinding.FragmentProjectListBinding
import com.cxj.jetpackmvvm.ext.startKtxActivity
import com.cxj.jetpackmvvm.ext.toast
import com.cxj.jetpackmvvm.model.pojo.QueryArticle
import com.cxj.jetpackmvvm.ui.article.*
import com.cxj.jetpackmvvm.ui.home.HomePageAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/15
 *      des:
 * </pre>
 */
class ProjectListFragment : BaseVMFragment<FragmentProjectListBinding>(R.layout.fragment_project_list) {
    private var page = 1
    private var cid = 0
    private val queryArticle by lazy { QueryArticle(page,cid,false) }
    private val mViewModel by viewModel<ProjectViewModel>()
    private val mAdapter by lazy { HomePageAdapter() }
    companion object {
        private const val PROJECT_CID = "PROJECT_CID"
        fun newInstance(cid: Int): ProjectListFragment{
            val args = Bundle()
            args.putInt(PROJECT_CID,cid)
            val fragment = ProjectListFragment()
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
            uiProjectArticleState.observe(this@ProjectListFragment, Observer {
                if (it.isLoading && !it.isRefresh) {//不是手动刷新动作 才展示loading
                    mAdapter.setEmptyView(getLoadingView())
                }

                it.isSuccess?.let { data->
                    mAdapter.run {
                        if (queryArticle.page == 1) {
                            setNewInstance(data.toMutableList())
                        }else {
                            addData(data)
                        }
                    }
                }
                it.isError?.let { msg->
                    toast(requireContext(),msg)
                    mAdapter.setEmptyView(getErrorView())
                }
            })
        }
    }
}