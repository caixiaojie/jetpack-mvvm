package com.cxj.jetpackmvvm.room.dao

import androidx.room.*
import com.cxj.jetpackmvvm.model.bean.BannerBean
import kotlinx.coroutines.flow.Flow

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/9
 *      des:
 * </pre>
 */
@Dao
interface BannerDao {
    @Query("SELECT * FROM banner_bean")
    suspend fun getBannerBeanList(): List<BannerBean>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertList(BannerBeanList: List<BannerBean>)

    @Transaction
    suspend fun insertList(BannerBeanList: List<BannerBean>) {
        for (banner in BannerBeanList) {
            insert(banner)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(BannerBean: BannerBean)

    @Update
    suspend fun update(BannerBean: BannerBean): Int

    @Delete
    suspend fun delete(BannerBean: BannerBean): Int

    @Delete
    suspend fun deleteList(BannerBeanList: List<BannerBean>): Int

    @Query("DELETE FROM banner_bean")
    suspend fun deleteAll()
}