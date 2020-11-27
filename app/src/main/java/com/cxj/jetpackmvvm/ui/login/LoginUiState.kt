package com.cxj.jetpackmvvm.ui.login

import com.cxj.jetpackmvvm.base.BaseViewModel


class LoginUiState<T>(
        isLoading: Boolean = false,
        isSuccess: T? = null,
        isError: String? = null,
        val enableLoginButton: Boolean = false,
        val needLogin: Boolean = false
) : BaseViewModel.UiState<T>(isLoading, false, isSuccess, isError)
