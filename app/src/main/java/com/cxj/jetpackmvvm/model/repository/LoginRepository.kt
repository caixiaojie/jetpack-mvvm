package com.cxj.jetpackmvvm.model.repository

import android.util.Log
import com.cxj.jetpackmvvm.base.BaseViewModel
import com.cxj.jetpackmvvm.model.api.BaseRepository
import com.cxj.jetpackmvvm.model.api.WanService
import com.cxj.jetpackmvvm.model.bean.*
import com.cxj.jetpackmvvm.ui.login.LoginUiState
import com.cxj.jetpackmvvm.util.MMkvHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import androidx.lifecycle.*

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/11/27
 *      des:
 * </pre>
 */
class LoginRepository(private val service: WanService) : BaseRepository() {

    @ExperimentalCoroutinesApi
    suspend fun loginFlow(userName: String, passWord: String) = flow {
        // 输入不能为空
        if (userName.isBlank() || passWord.isBlank()) {
            emit(LoginUiState(enableLoginButton = false))
            return@flow
        }
        service.login(userName, passWord).doSuccess { user ->
            MMkvHelper.getInstance().saveLogin(true)
            MMkvHelper.getInstance().saveUserInfo(user)
            emit(LoginUiState(isSuccess = user, enableLoginButton = true))
        }.doError { errorMsg ->
            emit(LoginUiState<User>(isError = errorMsg, enableLoginButton = true))
        }
    }.onStart { emit(LoginUiState(isLoading = true)) }.flowOn(Dispatchers.IO)
        .catch { emit(LoginUiState(isError = it.message, enableLoginButton = true)) }

    @ExperimentalCoroutinesApi
    suspend fun registerFlow(userName: String, passWord: String) = flow<LoginUiState<User>> {

        // 输入不能为空
        if (userName.isBlank() || passWord.isBlank()) {
            emit(LoginUiState(enableLoginButton = false))
            return@flow
        }

        service.register(userName, passWord, passWord).doSuccess {
            emit(LoginUiState(needLogin = true))//注册成功 立即登录
        }.doError { errorMsg ->
            emit(LoginUiState(isError = errorMsg, enableLoginButton = true))
        }
    }.onStart {
        emit(LoginUiState(isLoading = true))
    }.flowOn(Dispatchers.IO)
        .catch { emit(LoginUiState(isError = it.message, enableLoginButton = true)) }
}

