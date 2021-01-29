package com.cxj.jetpackmvvm.model.repository

import android.util.Log
import com.cxj.jetpackmvvm.base.BaseViewModel
import com.cxj.jetpackmvvm.model.api.BaseRepository
import com.cxj.jetpackmvvm.model.api.WanService
import com.cxj.jetpackmvvm.model.bean.*
import com.cxj.jetpackmvvm.model.pojo.QueryHomeArticle
import com.cxj.jetpackmvvm.room.WanDataBase
import com.cxj.jetpackmvvm.room.dao.BannerDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/9
 *      des:
 * </pre>
 */
class HomeRepository(private val service: WanService, private val dataBase: WanDataBase) :
    BaseRepository() {

    suspend fun getBanner(): Flow<BaseViewModel.UiState<List<BannerBean>>> {
        return flow {
            val bannerDao = dataBase.bannerDao()
            // 查询数据库是否存在，如果不存在请求网络
            var bannerBeanList = bannerDao.getBannerBeanList()
            if (bannerBeanList == null || bannerBeanList.isEmpty()) {
                // 网络请求
                service.getBanner().doSuccess { data ->
                    emit(BaseViewModel.UiState<List<BannerBean>>(isSuccess = data))
                    // 将网路请求的数据，换转成的数据库的 model，之后插入数据库
                    //                    infoModel = PokemonInfoEntity.convert2PokemonInfoEntity(netWorkPokemonInfo)
                    // 插入更新数据库
//                    insertBannerList(data,bannerDao)
                    bannerDao.insertList(data)
                }.doError { errorMsg ->
                    emit(BaseViewModel.UiState<List<BannerBean>>(isError = errorMsg))
                }
            } else {
                emit(BaseViewModel.UiState(isSuccess = bannerBeanList))
            }

        }.onStart { emit(BaseViewModel.UiState(isLoading = true)) }
            .catch { emit(BaseViewModel.UiState(isError = it.message, isLoading = false)) }
            .onCompletion { emit(BaseViewModel.UiState(isLoading = false)) }
            .flowOn(Dispatchers.IO)
    }

//    suspend fun getBanner1(): Flow<BaseViewModel.UiState<List<BannerBean>>> = fireds {
//        val bannerDao = dataBase.bannerDao()
//        // 查询数据库是否存在，如果不存在请求网络
//        var bannerBeanList = bannerDao.getBannerBeanList()
//        if (bannerBeanList == null || bannerBeanList.isEmpty()) {
//            // 网络请求
//            service.getBanner().doSuccess { data ->
//                emit(BaseViewModel.UiState<List<BannerBean>>(isSuccess = data))
//                // 将网路请求的数据，换转成的数据库的 model，之后插入数据库
//                //                    infoModel = PokemonInfoEntity.convert2PokemonInfoEntity(netWorkPokemonInfo)
//                // 插入更新数据库
////                    insertBannerList(data,bannerDao)
//                bannerDao.insertList(data)
//            }.doError { errorMsg ->
//                emit(BaseViewModel.UiState<List<BannerBean>>(isError = errorMsg))
//            }
//        } else {
//            emit(BaseViewModel.UiState(isSuccess = bannerBeanList))
//        }
//
//    }


    suspend fun getHomeArticle(query: QueryHomeArticle): Flow<BaseViewModel.UiState<List<Article>>> {
        return flow {
            service.getArticle(query.page).doSuccess { data ->
                emit(BaseViewModel.UiState<List<Article>>(isSuccess = data.datas,isRefresh = query.isRefresh))
            }.doError { errorMsg ->
                emit(BaseViewModel.UiState<List<Article>>(isError = errorMsg,isRefresh = query.isRefresh))
            }
        }.onStart { emit(BaseViewModel.UiState(isLoading = true,isRefresh = query.isRefresh)) }
            .catch { emit(BaseViewModel.UiState(isError = it.message, isLoading = false,isRefresh = query.isRefresh)) }
            .onCompletion { emit(BaseViewModel.UiState(isLoading = false,isRefresh = query.isRefresh)) }
            .flowOn(Dispatchers.IO)
    }


    private suspend fun insertBannerList(bannerList: List<BannerBean>,bannerDao: BannerDao) {
        val uiScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        try {
            uiScope.launch {
                bannerDao.insertList(bannerList)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ZHUJIANG", "insertBannerList onResourceReady: ${e.message}")
        }
    }
}