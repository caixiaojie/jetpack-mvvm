package com.cxj.jetpackmvvm.model.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/15
 *      des:
 * </pre>
 */
@Entity(tableName = "project_classify")
data class ProjectClassify(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    //@ColumnInfo(name = "children") val children: List<Any> = arrayListOf(),
    @ColumnInfo(name = "course_id") val courseId: Int,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "order_classify") val order: Int,
    @ColumnInfo(name = "parent_chapter_id") val parentChapterId: Int,
    @ColumnInfo(name = "user_control_set_top") val userControlSetTop: Boolean,
    @ColumnInfo(name = "visible") val visible: Int
)