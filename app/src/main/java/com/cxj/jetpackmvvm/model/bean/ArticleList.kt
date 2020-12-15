package com.cxj.jetpackmvvm.model.bean

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/9
 *      des:
 * </pre>
 */
data class ArticleList(
    val curPage: Int,
    val datas: List<Article>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)