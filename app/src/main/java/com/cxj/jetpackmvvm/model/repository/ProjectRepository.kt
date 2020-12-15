package com.cxj.jetpackmvvm.model.repository

import com.cxj.jetpackmvvm.base.BaseViewModel
import com.cxj.jetpackmvvm.model.api.BaseRepository
import com.cxj.jetpackmvvm.model.api.WanService
import com.cxj.jetpackmvvm.model.bean.*
import com.cxj.jetpackmvvm.model.pojo.QueryArticle
import com.cxj.jetpackmvvm.model.pojo.QueryHomeArticle
import com.cxj.jetpackmvvm.room.WanDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/15
 *      des:
 * </pre>
 */
class ProjectRepository(private val service: WanService, private val dataBase: WanDataBase) :
    BaseRepository() {
    suspend fun getProjectTree(): Flow<BaseViewModel.UiState<List<ProjectClassify>>> {
        return flow {
            val projectClassifyDao = dataBase.projectClassifyDao()
            val allProject = projectClassifyDao.getAllProject()
            if (allProject.isEmpty()) {
                service.getProjectTree().doSuccess { data->
                    emit(BaseViewModel.UiState(isSuccess = data))
                    projectClassifyDao.insertList(allProject)
                }.doError { errorMsg->
                    emit(BaseViewModel.UiState<List<ProjectClassify>>(isError = errorMsg))
                }
            }else {
                emit(BaseViewModel.UiState(isSuccess = allProject))
            }
        }.onStart { emit(BaseViewModel.UiState(isLoading = true)) }
            .catch { emit(BaseViewModel.UiState(isError = it.message, isLoading = false)) }
            .onCompletion { emit(BaseViewModel.UiState(isLoading = false)) }
            .flowOn(Dispatchers.IO)
    }


    suspend fun getProjectArticle(query: QueryArticle): Flow<BaseViewModel.UiState<List<Article>>> {
        return flow {
            service.getProject(query.page,query.cid).doSuccess { data ->
                emit(BaseViewModel.UiState<List<Article>>(isSuccess = data.datas,isRefresh = query.isRefresh))
            }.doError { errorMsg ->
                emit(BaseViewModel.UiState<List<Article>>(isError = errorMsg,isRefresh = query.isRefresh))
            }
        }.onStart { emit(BaseViewModel.UiState(isLoading = true,isRefresh = query.isRefresh)) }
            .catch { emit(BaseViewModel.UiState(isError = it.message, isLoading = false,isRefresh = query.isRefresh)) }
            .onCompletion { emit(BaseViewModel.UiState(isLoading = false,isRefresh = query.isRefresh)) }
            .flowOn(Dispatchers.IO)
    }
}