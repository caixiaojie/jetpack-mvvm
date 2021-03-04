package com.zdkj.reacthttp.datasource

import com.zdkj.reacthttp.bean.IHttpWrapBean
import com.zdkj.reacthttp.callback.RequestCallback
import com.zdkj.reacthttp.exception.BaseHttpException
import com.zdkj.reacthttp.exception.ServerCodeBadException
import com.zdkj.reacthttp.viewmodel.IUIActionEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlin.jvm.Throws

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/3/4
 *      des:
 * </pre>
 */
abstract class RemoteDataSource<Api : Any>(
    iuiActionEvent: IUIActionEvent?,
    apiServiceClass: Class<Api>,
) : BaseRemoteDataSource<Api>(iuiActionEvent, apiServiceClass) {

    fun <Data> enqueueLoading(
        apiFun: suspend Api.() -> IHttpWrapBean<Data>,
        baseUrl: String = "",
        callbackFun: (RequestCallback<Data>.() -> Unit)? = null,
    ) : Job{
        return enqueue(apiFun = apiFun,showLoading = true,baseUrl = baseUrl,callbackFun = callbackFun)
    }

    private fun <Data> enqueue(
        apiFun: suspend Api.() -> IHttpWrapBean<Data>,
        showLoading: Boolean,
        baseUrl: String,
        callbackFun: (RequestCallback<Data>.() -> Unit)?,
    ): Job {
        return launchMain {
            val callback = if (callbackFun == null) null else RequestCallback<Data>().apply {
                callbackFun.invoke(this)
            }
            try {
                if (showLoading) {
                    showLoading(coroutineContext[Job])
                }
                callback?.onStart?.invoke()
                val response: IHttpWrapBean<Data>
                try {
                    response = apiFun.invoke(getApiService(baseUrl))
                    if (!response.httpIsSuccess) {
                        throw ServerCodeBadException(response)
                    }
                }catch (throwable: Throwable) {
                    handleException(throwable, callback)
                    return@launchMain
                }
                onGetResponse(callback, response.httpData)
            }finally {
                try {
                    callback?.onFinally?.invoke()
                } finally {
                    if (showLoading) {
                        dismissLoading()
                    }
                }
            }
        }
    }

    /**
     * 获取原始数据请求（没有封装到IHttpWrapBean的实体类）
     */
    fun <Data> enqueueOriginLoading(apiFun: suspend Api.() -> Data,
                                    baseUrl: String = "",
                                    callbackFun: (RequestCallback<Data>.() -> Unit)? = null): Job {
        return enqueueOrigin(apiFun = apiFun, showLoading = true, baseUrl = baseUrl, callbackFun = callbackFun)
    }

    fun <Data> enqueueOrigin(apiFun: suspend Api.() -> Data,
                             showLoading: Boolean = false,
                             baseUrl: String = "",
                             callbackFun: (RequestCallback<Data>.() -> Unit)? = null): Job {
        return launchMain {
            val callback = if (callbackFun == null) null else RequestCallback<Data>().apply {
                callbackFun.invoke(this)
            }
            try {
                if (showLoading) {
                    showLoading(coroutineContext[Job])
                }
                callback?.onStart?.invoke()
                val response: Data
                try {
                    response = apiFun.invoke(getApiService(baseUrl))
                } catch (throwable: Throwable) {
                    handleException(throwable, callback)
                    return@launchMain
                }
                onGetResponse(callback, response)
            } finally {
                try {
                    callback?.onFinally?.invoke()
                } finally {
                    if (showLoading) {
                        dismissLoading()
                    }
                }
            }
        }
    }

    private suspend fun <Data> onGetResponse(callback: RequestCallback<Data>?, httpData: Data) {
        callback?.let {
            withNonCancellable {
                callback.onSuccess?.let {
                    withMain {
                        it.invoke(httpData)
                    }
                }
                callback.onSuccessIO?.let {
                    withIO {
                        it.invoke(httpData)
                    }
                }
            }
        }
    }

    /**
     * 同步请求，可能会抛出异常，外部需做好捕获异常的准备
     * @param apiFun
     */
    @Throws(BaseHttpException::class)
    fun <Data> execute(apiFun: suspend Api.() -> IHttpWrapBean<Data>, baseUrl: String = ""): Data {
        return runBlocking {
            try {
                val asyncIO = asyncIO {
                    apiFun.invoke(getApiService(baseUrl))
                }
                val response = asyncIO.await()
                if (response.httpIsSuccess) {
                    return@runBlocking response.httpData
                }
                throw ServerCodeBadException(response)
            } catch (throwable: Throwable) {
                throw generateBaseExceptionReal(throwable)
            }
        }
    }
}