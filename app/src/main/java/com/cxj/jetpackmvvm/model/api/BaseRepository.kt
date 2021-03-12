package com.cxj.jetpackmvvm.model.api

import android.util.Log
import com.cxj.jetpackmvvm.App
import com.cxj.jetpackmvvm.model.bean.WanResponse
import com.cxj.jetpackmvvm.base.WanResult
import com.zdkj.ktx.ext.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import androidx.lifecycle.*
import com.cxj.jetpackmvvm.base.BaseViewModel
import com.cxj.jetpackmvvm.model.bean.BannerBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/11/27
 *      des:
 * </pre>
 */
open class BaseRepository {
    suspend fun <T : Any> apiCall(call: suspend () -> WanResponse<T>): WanResponse<T> {
        return call.invoke()
    }

    suspend fun <T : Any> safeApiCall(
        call: suspend () -> WanResult<T>,
        errorMessage: String,
    ): WanResult<T> {
        return try {
            call()
        } catch (e: Exception) {
            // An exception was thrown when calling the API so we're converting this to an IOException
            WanResult.Failure(IOException(errorMessage, e))
        }
    }

    suspend fun <T : Any> executeResponse(
        response: WanResponse<T>, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null,
    ): WanResult<T> {
        return coroutineScope {
            if (response.errorCode == -1) {
                errorBlock?.let { it() }
                WanResult.Failure(IOException(response.errorMsg))
            } else {
                successBlock?.let { it() }
                WanResult.Success(response.data)
            }
        }
    }

    fun <T> fires(block: suspend () -> WanResponse<T>) = liveData<WanResult<T>> {
        val result = try {
            val baseModel = block()
            if (baseModel.errorCode == 0) {
                val model = baseModel.data
                WanResult.Success(model)
            } else {
                Log.e(
                    "ZHUJIANG哈哈fires",
                    "fires: response status is ${baseModel.errorCode}  msg is ${baseModel.errorMsg}"
                )
                toast(App.CONTEXT, baseModel.errorMsg)
                WanResult.Failure(RuntimeException(baseModel.errorMsg))
            }
        } catch (e: Exception) {
            Log.e("ZHUJIANG哈哈哈fires", e.toString())
            WanResult.Failure(e)
        }
        emit(result)
    }

    fun <T> fired(block: suspend () -> WanResponse<T>) = flow<BaseViewModel.UiState<T>> {
        val baseResponse = block()
        if (baseResponse.errorCode == 0) {
            val model = baseResponse.data
            emit(BaseViewModel.UiState(isSuccess = model))
        } else {
            Log.e(
                "ZHUJIANG哈哈fires",
                "fires: response status is ${baseResponse.errorCode}  msg is ${baseResponse.errorMsg}"
            )
            toast(App.CONTEXT, baseResponse.errorMsg)
            emit(BaseViewModel.UiState(isError = baseResponse.errorMsg, isLoading = false))
        }
    }.onStart { emit(BaseViewModel.UiState(isLoading = true)) }
        .catch {
            emit(BaseViewModel.UiState(isError = it.message, isLoading = false))
        }
        .onCompletion { emit(BaseViewModel.UiState(isLoading = false)) }
        .flowOn(Dispatchers.IO)

    fun <T> fireds(function: suspend () -> WanResponse<List<BannerBean>>) =
        flow<BaseViewModel.UiState<T>> {

        }.onStart { emit(BaseViewModel.UiState(isLoading = true)) }
            .catch {
                emit(BaseViewModel.UiState(isError = it.message, isLoading = false))
            }
            .onCompletion { emit(BaseViewModel.UiState(isLoading = false)) }
            .flowOn(Dispatchers.IO)


    fun <T> fire(block: suspend () -> Result<T>) = liveData<Result<T>> {
        val result = try {
            block()
        } catch (e: Exception) {
            Log.e("ZHUJIANG哈哈哈fire", e.toString())
            Result.failure(e)
        }
        emit(result)
    }
}



