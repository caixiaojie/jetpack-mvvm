package com.cxj.jetpackmvvm.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cxj.jetpackmvvm.model.bean.BannerBean
import com.cxj.jetpackmvvm.model.bean.ProjectClassify
import com.cxj.jetpackmvvm.room.dao.BannerDao
import com.cxj.jetpackmvvm.room.dao.ProjectClassifyDao

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/9
 *      des:
 * </pre>
 */
@Database(
    entities = [BannerBean::class,ProjectClassify::class],
    version = 1
)
abstract class WanDataBase : RoomDatabase() {

    abstract fun bannerDao(): BannerDao
    abstract fun projectClassifyDao(): ProjectClassifyDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: WanDataBase? = null

        fun getDatabase(context: Context): WanDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WanDataBase::class.java,
                    "wan_database"
                )
//                    .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}