package com.cxj.jetpackmvvm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.room.withTransaction
import com.cxj.jetpackmvvm.base.BaseViewModel
import com.cxj.jetpackmvvm.model.bean.Article
import com.cxj.jetpackmvvm.model.bean.BannerBean
import com.cxj.jetpackmvvm.model.pojo.QueryHomeArticle
import com.cxj.jetpackmvvm.model.repository.HomeRepository
import com.cxj.jetpackmvvm.room.WanDataBase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@FlowPreview
@ExperimentalCoroutinesApi
class HomeViewModel(private val repository: HomeRepository) : BaseViewModel() {
    private val pageLiveData = MutableLiveData<QueryHomeArticle>()

    private val _mHomeArticleInfo : MutableLiveData<UiState<List<Article>>> = MutableLiveData()
    val uiHomeArticleState: LiveData<UiState<List<Article>>>
        get() = _mHomeArticleInfo


    private val _mBannerInfo : MutableLiveData<UiState<List<BannerBean>>> = MutableLiveData()
    val uiHomeBannerState: LiveData<UiState<List<BannerBean>>>
        get() = _mBannerInfo


    fun getHomeArticle(query:QueryHomeArticle) = launchOnUI {
        repository.getHomeArticle(query).collect {
            _mHomeArticleInfo.value = it
        }
    }

    fun getHomeBanner() {
        launchOnUI {
            repository.getBanner().collect {

                _mBannerInfo.value = it
            }
        }
    }
}