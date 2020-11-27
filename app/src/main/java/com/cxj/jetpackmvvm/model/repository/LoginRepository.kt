package com.cxj.jetpackmvvm.model.repository

import com.cxj.jetpackmvvm.model.api.BaseRepository
import com.cxj.jetpackmvvm.model.api.WanService
import com.cxj.jetpackmvvm.model.bean.User
import com.cxj.jetpackmvvm.model.bean.doError
import com.cxj.jetpackmvvm.model.bean.doSuccess
import com.cxj.jetpackmvvm.ui.login.LoginUiState
import com.cxj.jetpackmvvm.util.Preference
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/11/27
 *      des:
 * </pre>
 */
class LoginRepository(val service: WanService) : BaseRepository() {
    private var isLogin by Preference(Preference.IS_LOGIN, false)
    private var userJson by Preference(Preference.USER_GSON, "")

    @ExperimentalCoroutinesApi
    suspend fun loginFlow(userName: String, passWord: String) = flow {
        // 输入不能为空
        if (userName.isBlank() || passWord.isBlank()) {
            emit(LoginUiState(enableLoginButton = false))
            return@flow
        }
        service.login(userName, passWord).doSuccess { user ->
            isLogin = true
            userJson = Gson().toJson(user)
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