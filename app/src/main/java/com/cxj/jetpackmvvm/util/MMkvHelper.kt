package com.cxj.jetpackmvvm.util

import com.cxj.jetpackmvvm.C
import com.cxj.jetpackmvvm.model.bean.User
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.tencent.mmkv.MMKV
import java.util.ArrayList

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/7
 *      des:
 * </pre>
 */
class MMkvHelper private constructor(val mmkv: MMKV = MMKV.defaultMMKV()){
    private val mGson = Gson()
    companion object {
        private var mInstance: MMkvHelper? = null
            get() {
                return field ?: MMkvHelper()
            }

        @JvmStatic
        @Synchronized//添加synchronized同步锁
        fun getInstance(): MMkvHelper {
            return requireNotNull(mInstance)
        }

    }

    fun saveLogin(isLogin: Boolean = false) {
        mmkv.encode(C.IS_LOGIN,isLogin)
    }

    /**
     * 保存用户信息
     */
    fun saveUserInfo(userInfo: User?) {
        mmkv.encode(C.USER_INFO, userInfo)
    }

    val isLogin: Boolean
        get() = mmkv.decodeBool(C.IS_LOGIN,false)

    val userInfo: User
        get() = mmkv.decodeParcelable(C.USER_INFO, User::class.java)

    fun <T> saveProjectTabs(dataList: List<T>?) {
        saveList(C.PROJECT_TABS, dataList)
    }

    fun <T> getProjectTabs(cls: Class<T>): List<T> {
        return getTList(C.PROJECT_TABS, cls)
    }

    /**
     * 保存list
     */
    private fun <T> saveList(tag: String, dataList: List<T>?) {
        if (null == dataList || dataList.size <= 0) return
        //转换成json数据，再保存
        val strJson = mGson.toJson(dataList)
        mmkv.encode(tag, strJson)
    }

    private fun <T> getTList(tag: String, cls: Class<T>): List<T> {
        val dataList: MutableList<T> = ArrayList()
        val strJson = mmkv.decodeString(tag, null)
            ?: return dataList
        try {
            val gson = Gson()
            val arry = JsonParser().parse(strJson).asJsonArray
            for (jsonElement in arry) {
                dataList.add(gson.fromJson(jsonElement, cls))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return dataList
    }

    fun logout() {
        LogUtils.i("logout")
        saveLogin()
        mmkv.remove(C.USER_INFO)
    }
}