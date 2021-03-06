package com.cxj.jetpackmvvm.model.bean

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import com.cxj.jetpackmvvm.base.WanResult

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/11/27
 *      des:
 * </pre>
 */

data class WanResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T)

suspend fun <T : Any> WanResponse<T>.executeResponse(successBlock: (suspend CoroutineScope.() -> Unit)? = null,
                                                     errorBlock: (suspend CoroutineScope.() -> Unit)? = null): WanResult<T> {
    return coroutineScope {
        if (errorCode == -1) {
            errorBlock?.let { it() }
            WanResult.Failure(IOException(errorMsg))
        } else {
            successBlock?.let { it() }
            WanResult.Success(data)
        }
    }
}

suspend fun <T : Any> WanResponse<T>.doSuccess(successBlock: (suspend CoroutineScope.(T) -> Unit)? = null): WanResponse<T> {
    return coroutineScope {
        if (errorCode != -1) successBlock?.invoke(this, this@doSuccess.data)
        this@doSuccess
    }
}

suspend fun <T : Any> WanResponse<T>.doError(errorBlock: (suspend CoroutineScope.(String) -> Unit)? = null): WanResponse<T> {
    return coroutineScope {
        if (errorCode == -1) errorBlock?.invoke(this, this@doError.errorMsg)
        this@doError
    }
}