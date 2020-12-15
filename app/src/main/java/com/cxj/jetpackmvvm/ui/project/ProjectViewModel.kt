package com.cxj.jetpackmvvm.ui.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.room.withTransaction
import com.cxj.jetpackmvvm.base.BaseViewModel
import com.cxj.jetpackmvvm.model.bean.Article
import com.cxj.jetpackmvvm.model.bean.BannerBean
import com.cxj.jetpackmvvm.model.bean.ProjectClassify
import com.cxj.jetpackmvvm.model.pojo.QueryArticle
import com.cxj.jetpackmvvm.model.pojo.QueryHomeArticle
import com.cxj.jetpackmvvm.model.repository.HomeRepository
import com.cxj.jetpackmvvm.model.repository.ProjectRepository
import com.cxj.jetpackmvvm.room.WanDataBase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@FlowPreview
@ExperimentalCoroutinesApi
class ProjectViewModel(private val repository: ProjectRepository) : BaseViewModel() {

    private val _mProjectTreeInfo : MutableLiveData<UiState<List<ProjectClassify>>> = MutableLiveData()
    val uiProjectTreeState: LiveData<UiState<List<ProjectClassify>>>
        get() = _mProjectTreeInfo


    private val _mProjectArticleInfo : MutableLiveData<UiState<List<Article>>> = MutableLiveData()
    val uiProjectArticleState: LiveData<UiState<List<Article>>>
        get() = _mProjectArticleInfo


    fun getProjectTree() = launchOnUI {
        repository.getProjectTree().collect {
            _mProjectTreeInfo.value = it
        }
    }

    fun getProjectArticle(query:QueryArticle) = launchOnUI {
        repository.getProjectArticle(query).collect {
            _mProjectArticleInfo.value = it
        }
    }
}