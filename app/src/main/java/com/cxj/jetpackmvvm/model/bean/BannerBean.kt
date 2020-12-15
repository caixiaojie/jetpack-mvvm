package com.cxj.jetpackmvvm.model.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/9
 *      des:
 * </pre>
 */
@Entity(tableName = "banner_bean")
data class BannerBean(
//    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "desc")val desc: String,
    @PrimaryKey
    @ColumnInfo(name = "id")val id: Int,
    @ColumnInfo(name = "imagePath")val imagePath: String,
    @ColumnInfo(name = "isVisible")val isVisible: Int,
    @ColumnInfo(name = "order")val order: Int,
    @ColumnInfo(name = "title")val title: String,
    @ColumnInfo(name = "type")val type: Int,
    @ColumnInfo(name = "url")val url: String,
)