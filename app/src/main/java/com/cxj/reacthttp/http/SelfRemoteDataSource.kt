package com.cxj.reacthttp.http

import android.widget.Toast
import com.cxj.jetpackmvvm.App
import com.cxj.jetpackmvvm.BuildConfig
import com.cxj.jetpackmvvm.ext.toast
import com.zdkj.reacthttp.datasource.RemoteExtendDataSource
import com.zdkj.reacthttp.viewmodel.IUIActionEvent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/3/5
 *      des:
 * </pre>
 */
class SelfRemoteDataSource(iuiActionEvent: IUIActionEvent?) : RemoteExtendDataSource<ApiService>(iuiActionEvent,ApiService::class.java) {
    companion object {
        private val httpClient: OkHttpClient by lazy {
            createHttpClient()
        }

        private fun createHttpClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.BASIC
            }
            val builder = OkHttpClient.Builder()
                .readTimeout(1000L, TimeUnit.MILLISECONDS)
                .writeTimeout(1000L, TimeUnit.MILLISECONDS)
                .connectTimeout(1000L, TimeUnit.MILLISECONDS)
                .addInterceptor(logging)
                .retryOnConnectionFailure(true)
            return builder.build()
        }
    }

    override fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override val baseUrl: String
        get() = HttpConfig.BASE_URL

    override fun showToast(msg: String) {
        toast(App.CONTEXT,msg,Toast.LENGTH_SHORT)
    }
}