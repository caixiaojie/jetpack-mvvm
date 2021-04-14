package com.cxj.reacthttp.bean

/**
 * Created by 二哈 on 2020/9/27.
 * 我的页面 返回json实体
 */
data class MyPageBean(
    val efficiency: Int,//平均效率 单位:min/次
    val illegalTimes: Int,//违规次数
    val quality: Int,//质量
    val totalWorkTimes: Int,//工作次数
    val totalWorks: String,//累计工时:外委人员:累计登陆时长 单位min
    val workType: String//工作类型
){
    fun getMyTotalWorks() : String{
        try {
            val toLong = totalWorks.toLong()
            val hour = toLong / 60
            val minute = toLong % 60
            return if (hour < 1) {
                "${minute}min"
            }else {
                if (minute == 0L) {
                    "${hour}h"
                }else {
                    "${hour}h${minute}min"
                }
            }
        }catch (e: Exception) {
            return "0min"
        }
    }
}