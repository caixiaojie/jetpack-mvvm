package com.zdkj.uilib.anno

import androidx.annotation.IntDef
import com.zdkj.uilib.anno.EmptyViewState.Companion.STATE_EMPTY
import com.zdkj.uilib.anno.EmptyViewState.Companion.STATE_LOADING


/**
 * 列表为空的控件当前的状态
 */
@IntDef(value = [STATE_LOADING, STATE_EMPTY])
@Target(allowedTargets = [AnnotationTarget.FIELD,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER])
annotation class EmptyViewState {
    companion object {
        /** 加载中 */
        const val STATE_LOADING                     = 0
        /** 列表为空 */
        const val STATE_EMPTY                       = 1
    }
}