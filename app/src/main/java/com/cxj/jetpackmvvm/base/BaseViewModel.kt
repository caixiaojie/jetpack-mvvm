package com.cxj.jetpackmvvm.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.cxj.jetpackmvvm.App
import com.cxj.jetpackmvvm.model.bean.WanResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.Result


open class BaseViewModel : ViewModel() {

    open class UiState<T>(
            val isLoading: Boolean = false,
            val isRefresh: Boolean = false,//是否是手动刷新 手动刷新不显示loading（刷新控件自带有loading）
            val isSuccess: T? = null,
            val isError: String?= null
    )


    open class BaseUiModel<T>(
            var showLoading: Boolean = false,
            var showError: String? = null,
            var showSuccess: T? = null,
            var showEnd: Boolean = false, // 加载更多
            var isRefresh: Boolean = false // 刷新

    )

    val mException: MutableLiveData<Throwable> = MutableLiveData()


    fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {

        viewModelScope.launch { block() }

    }

    suspend fun <T> launchOnIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block
        }
    }
}

