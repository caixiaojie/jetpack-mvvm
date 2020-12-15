package com.cxj.jetpackmvvm.room.dao

import androidx.room.*
import com.cxj.jetpackmvvm.model.bean.ProjectClassify

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/15
 *      des:
 * </pre>
 */
@Dao
interface ProjectClassifyDao {
    @Query("SELECT * FROM project_classify where order_classify>144999 and order_classify<145050")
    suspend fun getAllProject(): List<ProjectClassify>

    @Query("SELECT * FROM project_classify where order_classify>189999 and order_classify<190020")
    suspend fun getAllOfficial(): List<ProjectClassify>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(projectClassifyList: List<ProjectClassify>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(projectClassify: ProjectClassify)

    @Delete
    suspend fun delete(projectClassify: ProjectClassify): Int

    @Delete
    suspend fun deleteList(projectClassifyList: List<ProjectClassify>): Int

    @Query("DELETE FROM project_classify")
    suspend fun deleteAll()
}