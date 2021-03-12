package com.cxj.jetpackmvvm.model.api

import com.cxj.jetpackmvvm.App
import com.zdkj.ktx.ext.isConnectedNetwork
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import java.io.File

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/11/27
 *      des:
 * </pre>
 */
object WanRetrofitClient : BaseRetrofitClient() {
    val service by lazy { getService(WanService::class.java, WanService.BASE_URL) }
    private val cookieJar by lazy {
        PersistentCookieJar(
            SetCookieCache(), SharedPrefsCookiePersistor(
                App.CONTEXT
            )
        )
    }

    override fun handleBuilder(builder: OkHttpClient.Builder) {
        val httpCacheDirectory = File(App.CONTEXT.cacheDir, "response")
        val cacheSize = 10 * 1024 * 1024L // 10 MiB
        val cache = Cache(httpCacheDirectory, cacheSize)
        builder.cache(cache)
            .cookieJar(cookieJar)
            .addInterceptor { chain ->
                var request = chain.request()
                if (!App.CONTEXT.isConnectedNetwork()) {
                    request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
                }
                val response = chain.proceed(request)
                if (!App.CONTEXT.isConnectedNetwork()) {
                    val maxAge = 60 * 60
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .build()
                } else {
                    val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
                }
                response
            }
    }
}