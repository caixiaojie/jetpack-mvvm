package com.zdkj.reacthttp.datasource

import com.zdkj.reacthttp.bean.IHttpWrapBean
import com.zdkj.reacthttp.callback.RequestPairCallback
import com.zdkj.reacthttp.callback.RequestTripleCallback
import com.zdkj.reacthttp.exception.ServerCodeBadException
import com.zdkj.reacthttp.viewmodel.IUIActionEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/3/4
 *      des:
 * </pre>
 */
abstract class RemoteExtendDataSource<Api : Any>(iActionEvent: IUIActionEvent?, apiServiceClass: Class<Api>) : RemoteDataSource<Api>(iActionEvent, apiServiceClass) {

    fun <DataA, DataB> enqueueLoading(apiFunA: suspend Api.() -> IHttpWrapBean<DataA>,
                                      apiFunB: suspend Api.() -> IHttpWrapBean<DataB>,
                                      callbackFun: (RequestPairCallback<DataA, DataB>.() -> Unit)? = null): Job {
        return enqueue(apiFunA = apiFunA, apiFunB = apiFunB, showLoading = true, callbackFun = callbackFun)
    }

    fun <DataA, DataB> enqueue(apiFunA: suspend Api.() -> IHttpWrapBean<DataA>,
                               apiFunB: suspend Api.() -> IHttpWrapBean<DataB>,
                               showLoading: Boolean = false,
                               callbackFun: (RequestPairCallback<DataA, DataB>.() -> Unit)? = null): Job {
        return launchMain {
            val callback = if (callbackFun == null) null else RequestPairCallback<DataA, DataB>().apply {
                callbackFun.invoke(this)
            }
            try {
                if (showLoading) {
                    showLoading(coroutineContext[Job])
                }
                callback?.onStart?.invoke()
                val responseList: List<IHttpWrapBean<out Any?>>
                try {
                    responseList = listOf(
                        async { apiFunA.invoke(getApiService()) },
                        async { apiFunB.invoke(getApiService()) }
                    ).awaitAll()
                    val failed = responseList.find { it.httpIsFailed }
                    if (failed != null) {
                        throw ServerCodeBadException(failed)
                    }
                } catch (throwable: Throwable) {
                    handleException(throwable, callback)
                    return@launchMain
                }
                onGetResponse(callback, responseList)
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

    private suspend fun <DataA, DataB> onGetResponse(callback: RequestPairCallback<DataA, DataB>?,
                                                     responseList: List<IHttpWrapBean<out Any?>>) {
        callback?.let {
            withNonCancellable {
                callback.onSuccess?.let {
                    withMain {
                        it.invoke(responseList[0].httpData as DataA, responseList[1].httpData as DataB)
                    }
                }
                callback.onSuccessIO?.let {
                    withIO {
                        it.invoke(responseList[0].httpData as DataA, responseList[1].httpData as DataB)
                    }
                }
            }
        }
    }

    fun <DataA, DataB, DataC> enqueueLoading(apiFunA: suspend Api.() -> IHttpWrapBean<DataA>,
                                             apiFunB: suspend Api.() -> IHttpWrapBean<DataB>,
                                             apiFunC: suspend Api.() -> IHttpWrapBean<DataC>,
                                             baseUrl: String = "",
                                             callbackFun: (RequestTripleCallback<DataA, DataB, DataC>.() -> Unit)? = null): Job {
        return enqueue(apiFunA = apiFunA, apiFunB = apiFunB, apiFunC = apiFunC, showLoading = true, baseUrl = baseUrl, callbackFun = callbackFun)
    }

    fun <DataA, DataB, DataC> enqueue(apiFunA: suspend Api.() -> IHttpWrapBean<DataA>,
                                      apiFunB: suspend Api.() -> IHttpWrapBean<DataB>,
                                      apiFunC: suspend Api.() -> IHttpWrapBean<DataC>,
                                      showLoading: Boolean = false,
                                      baseUrl: String = "",
                                      callbackFun: (RequestTripleCallback<DataA, DataB, DataC>.() -> Unit)? = null): Job {
        return launchMain {
            val callback = if (callbackFun == null) null else RequestTripleCallback<DataA, DataB, DataC>().apply {
                callbackFun.invoke(this)
            }
            try {
                if (showLoading) {
                    showLoading(coroutineContext[Job])
                }
                val responseList: List<IHttpWrapBean<out Any?>>
                try {
                    responseList = listOf(
                        async { apiFunA.invoke(getApiService(baseUrl)) },
                        async { apiFunB.invoke(getApiService(baseUrl)) },
                        async { apiFunC.invoke(getApiService(baseUrl)) }
                    ).awaitAll()
                    val failed = responseList.find { it.httpIsFailed }
                    if (failed != null) {
                        throw ServerCodeBadException(failed)
                    }
                } catch (throwable: Throwable) {
                    handleException(throwable, callback)
                    return@launchMain
                }
                onGetResponse(callback, responseList)
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

    private suspend fun <DataA, DataB, DataC> onGetResponse(callback: RequestTripleCallback<DataA, DataB, DataC>?,
                                                            responseList: List<IHttpWrapBean<out Any?>>) {
        callback?.let {
            withNonCancellable {
                callback.onSuccess?.let {
                    withMain {
                        it.invoke(responseList[0].httpData as DataA, responseList[1].httpData as DataB, responseList[2].httpData as DataC)
                    }
                }
                callback.onSuccessIO?.let {
                    withIO {
                        it.invoke(responseList[0].httpData as DataA, responseList[1].httpData as DataB, responseList[2].httpData as DataC)
                    }
                }
            }
        }
    }

}