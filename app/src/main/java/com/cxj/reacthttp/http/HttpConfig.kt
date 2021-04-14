package com.cxj.reacthttp.http

import com.cxj.jetpackmvvm.BuildConfig

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/3/5
 *      des:
 * </pre>
 */
object HttpConfig {
//    const val BASE_URL = "https://www.wanandroid.com"
//    const val BASE_URL = "http://172.30.200.99:9898"
    const val BASE_URL = BuildConfig.SERVER_IP

    //服务端返回的 code 以 CODE_SERVER 开头
    const val CODE_SERVER_FAIL = -1
    const val CODE_SERVER_SUCCESS = 0
}