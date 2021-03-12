package com.zdkj.ktx.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.zdkj.ktx.ext.loge
import zdkj.util.ktx.core.lifecycle.KtxManager

/**
 *
 * on 2019/8/6 10:45
 */
class KtxLifeCycleCallBack : Application.ActivityLifecycleCallbacks {


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        KtxManager.pushActivity(activity)
        "onActivityCreated : ${activity.localClassName}".loge()
    }

    override fun onActivityStarted(activity: Activity) {
        "onActivityStarted : ${activity.localClassName}".loge()
    }

    override fun onActivityResumed(activity: Activity) {
        "onActivityResumed : ${activity.localClassName}".loge()
    }

    override fun onActivityPaused(activity: Activity) {
        "onActivityPaused : ${activity.localClassName}".loge()
    }


    override fun onActivityDestroyed(activity: Activity) {
        "onActivityDestroyed : ${activity.localClassName}".loge()
        KtxManager.popActivity(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
        "onActivityStopped : ${activity.localClassName}".loge()
    }


}