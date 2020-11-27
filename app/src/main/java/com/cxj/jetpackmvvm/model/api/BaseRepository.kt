package com.cxj.jetpackmvvm.model.api

import com.cxj.jetpackmvvm.model.bean.WanResponse
import com.cxj.jetpackmvvm.base.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import java.io.IOException

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

    suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> {
        return try {
            call()
        } catch (e: Exception) {
            // An exception was thrown when calling the API so we're converting this to an IOException
            Result.Error(IOException(errorMessage, e))
        }
    }

    suspend fun <T : Any> executeResponse(response: WanResponse<T>, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
                                          errorBlock: (suspend CoroutineScope.() -> Unit)? = null): Result<T> {
        return coroutineScope {
            if (response.errorCode == -1) {
                errorBlock?.let { it() }
                Result.Error(IOException(response.errorMsg))
            } else {
                successBlock?.let { it() }
                Result.Success(response.data)
            }
        }
    }

}