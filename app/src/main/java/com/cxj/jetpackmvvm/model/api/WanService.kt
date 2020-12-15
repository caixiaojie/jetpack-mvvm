package com.cxj.jetpackmvvm.model.api

import com.cxj.jetpackmvvm.model.bean.*
import retrofit2.Call
import retrofit2.http.*

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/11/27
 *      des:
 * </pre>
 */
interface WanService {
    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@Field("username") userName: String, @Field("password") passWord: String): WanResponse<User>

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(@Field("username") userName: String, @Field("password") passWord: String, @Field("repassword") rePassWord: String): WanResponse<User>

    @GET("banner/json")
    suspend fun getBanner(): WanResponse<List<BannerBean>>

    @GET("article/top/json")
    suspend fun getTopArticle(): WanResponse<List<Article>>

    @GET("article/list/{a}/json")
    suspend fun getArticle(@Path("a") a: Int): WanResponse<ArticleList>

    @GET("project/tree/json")
    suspend fun getProjectTree(): WanResponse<List<ProjectClassify>>

    @GET("project/list/{page}/json")
    suspend fun getProject(@Path("page") page: Int, @Query("cid") cid: Int): WanResponse<ArticleList>
}