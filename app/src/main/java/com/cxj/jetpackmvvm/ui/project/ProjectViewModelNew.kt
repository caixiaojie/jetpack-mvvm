package com.cxj.jetpackmvvm.ui.project

import androidx.lifecycle.MutableLiveData
import com.cxj.jetpackmvvm.model.bean.Article
import com.cxj.jetpackmvvm.model.bean.ProjectClassify
import com.cxj.jetpackmvvm.model.bean.doError
import com.cxj.jetpackmvvm.model.bean.doSuccess
import com.cxj.jetpackmvvm.model.pojo.QueryArticle
import com.cxj.jetpackmvvm.room.WanDataBase
import com.cxj.reacthttp.base.BaseViewModel

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/3/5
 *      des:
 * </pre>
 */
class ProjectViewModelNew : BaseViewModel() {

    val projectTreeData = MutableLiveData<List<ProjectClassify>>()
    val projectArticleData = MutableLiveData<List<Article>>()

    fun testTokenInvalid() {
        remoteDataSource.enqueueLoading(
            {
                myPage(2,"2")
            }
        ) {
            onSuccess {

            }
        }
    }

    fun getProjectTree() {
        remoteDataSource.enqueueLoading(
            {
                getProjectTree()
            }
        ) {
            onSuccess {
                projectTreeData.value = it
            }
        }
    }

    fun getProjectArticle(query: QueryArticle) {
        remoteDataSource.enqueueLoading(
            { getProject(query.page, query.cid) }
        ) {
            onSuccess {
                projectArticleData.value = it.datas
            }
        }
    }
}