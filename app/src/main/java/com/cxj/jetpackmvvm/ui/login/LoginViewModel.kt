package com.cxj.jetpackmvvm.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cxj.jetpackmvvm.base.BaseViewModel
import com.cxj.jetpackmvvm.model.bean.User
import com.cxj.jetpackmvvm.model.repository.LoginRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/11/27
 *      des:
 * </pre>
 */
class LoginViewModel(val repository: LoginRepository) : BaseViewModel() {
    val userName = ObservableField<String>("")
    val passWord = ObservableField<String>("")

    private val _uiState = MutableLiveData<LoginUiState<User>>()
    val uiState: LiveData<LoginUiState<User>>
        get() = _uiState

    private fun isInputValid(userName: String, passWord: String) = userName.isNotBlank() && passWord.isNotBlank()

    private fun loginDataChanged() {
        _uiState.value = LoginUiState(enableLoginButton = isInputValid(userName.get()
            ?: "", passWord.get() ?: ""))
    }

    @ExperimentalCoroutinesApi
    fun login() {
        launchOnUI {
            repository.loginFlow(userName.get() ?: "", passWord.get() ?: "")
                .collect {
                    _uiState.postValue(it)
                }
        }
    }

    @ExperimentalCoroutinesApi
    fun register() {
        launchOnUI {
            repository.registerFlow(userName.get() ?: "", passWord.get() ?: "")
                .collect {
                    _uiState.postValue(it)
                }
        }
    }

    val verifyInput: (String) -> Unit = { loginDataChanged() }


}