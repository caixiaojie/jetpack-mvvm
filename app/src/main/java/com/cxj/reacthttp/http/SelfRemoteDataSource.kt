package com.cxj.reacthttp.http

import android.widget.Toast
import com.cxj.jetpackmvvm.App
import com.cxj.jetpackmvvm.BuildConfig
import com.zdkj.ktx.ext.toast
import com.cxj.reacthttp.event.TokenInvalidEvent
import com.zdkj.reacthttp.callback.BaseRequestCallback
import com.zdkj.reacthttp.datasource.RemoteExtendDataSource
import com.zdkj.reacthttp.exception.BaseHttpException
import com.zdkj.reacthttp.exception.LocalBadException
import com.zdkj.reacthttp.exception.TokenInvalidException
import com.zdkj.reacthttp.viewmodel.IUIActionEvent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.greenrobot.eventbus.EventBus
import retrofit2.HttpException
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

    override fun handleException(throwable: Throwable, callback: BaseRequestCallback?) {
        super.handleException(throwable, callback)
        val exception = generateBaseExceptionReal(throwable)
        //登录失效 发送一个通知到baseActivity去处理响应的跳转逻辑
        //这种情况是http请求返回401
        if (throwable is HttpException) {
            if (throwable.code() == BaseHttpException.CODE_TOKEN_INVALID) {
                EventBus.getDefault().post(TokenInvalidEvent())
            }
        }
        //这种情况是内部服务器请求通了 后端报错的登录失效
        if (exception is TokenInvalidException) {
            EventBus.getDefault().post(TokenInvalidEvent())
        }
    }

    override fun exceptionFormat(httpException: BaseHttpException): String {
        if (httpException is LocalBadException) {
            if (httpException.throwable is HttpException) {
                if ((httpException.throwable as HttpException).code() == BaseHttpException.CODE_TOKEN_INVALID) {
                    return "未登录或会话已过期"
                }
            }
        }
        return super.exceptionFormat(httpException)
    }

    override val baseUrl: String
        get() = HttpConfig.BASE_URL

    override fun showToast(msg: String) {
        toast(App.CONTEXT,msg,Toast.LENGTH_SHORT)
    }
}