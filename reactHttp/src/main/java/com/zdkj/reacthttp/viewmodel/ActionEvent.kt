package com.zdkj.reacthttp.viewmodel

import kotlinx.coroutines.Job

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/3/4
 *      des:
 * </pre>
 */
open class BaseActionEvent

class ShowLoadingEvent(val job: Job?) : BaseActionEvent()

object DismissLoadingEvent : BaseActionEvent()

object FinishViewEvent : BaseActionEvent()

class ShowToastEvent(val message: String) : BaseActionEvent()