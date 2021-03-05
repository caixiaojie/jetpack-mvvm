package com.cxj.reacthttp.http

import com.cxj.jetpackmvvm.model.bean.ArticleList
import com.cxj.jetpackmvvm.model.bean.ProjectClassify
import com.cxj.jetpackmvvm.model.bean.User
import com.cxj.jetpackmvvm.model.bean.WanResponse
import com.cxj.reacthttp.bean.HttpWrapBean
import retrofit2.http.*

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/3/5
 *      des:
 * </pre>
 */
interface ApiService {

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@Field("username") userName: String, @Field("password") passWord: String): HttpWrapBean<User>

    @GET("project/tree/json")
    suspend fun getProjectTree(): HttpWrapBean<List<ProjectClassify>>

    @GET("project/list/{page}/json")
    suspend fun getProject(@Path("page") page: Int, @Query("cid") cid: Int): HttpWrapBean<ArticleList>
}