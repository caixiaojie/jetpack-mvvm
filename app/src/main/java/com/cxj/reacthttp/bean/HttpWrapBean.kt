package com.cxj.reacthttp.bean

import com.cxj.reacthttp.http.HttpConfig
import com.google.gson.annotations.SerializedName
import com.zdkj.reacthttp.bean.IHttpWrapBean

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/3/5
 *      des:
 * </pre>
 */
class HttpWrapBean<T>(
    @SerializedName("errorCode") val errorCode: Int = 0,
    @SerializedName("errorMsg") val errorMsg: String? = null,
    @SerializedName("data") val data: T,
) : IHttpWrapBean<T> {
    companion object {
        fun <T> success(data: T): HttpWrapBean<T> {
            return HttpWrapBean(HttpConfig.CODE_SERVER_SUCCESS, "success", data)
        }

        fun <T> failed(data: T): HttpWrapBean<T> {
            return HttpWrapBean(-200, "服务器停止维护了~~", data)
        }
    }

    override val httpCode: Int
        get() = errorCode
    override val httpMsg: String
        get() = errorMsg ?: ""
    override val httpData: T
        get() = data
    override val httpIsSuccess: Boolean
        get() = errorCode != HttpConfig.CODE_SERVER_FAIL

    override fun toString(): String {
        return "HttpWrapBean(errorCode=$errorCode, errorMsg=$errorMsg, data=$data)"
    }


}
