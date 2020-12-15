package com.cxj.jetpackmvvm.base

sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}

sealed class WanResult<out T> {
    data class Success<out T>(val value: T) : WanResult<T>()

    data class Failure(val throwable: Throwable?) : WanResult<Nothing>()
}

inline fun <reified T> WanResult<T>.doSuccess(success: (T) -> Unit) {
    if (this is WanResult.Success) {
        success(value)
    }
}

inline fun <reified T> WanResult<T>.doFailure(failure: (Throwable?) -> Unit) {
    if (this is WanResult.Failure) {
        failure(throwable)
    }
}