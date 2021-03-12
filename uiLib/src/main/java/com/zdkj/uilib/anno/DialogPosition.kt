package com.zdkj.uilib.anno

import androidx.annotation.IntDef
import com.zdkj.uilib.anno.DialogPosition.Companion.POS_BOTTOM
import com.zdkj.uilib.anno.DialogPosition.Companion.POS_CENTER
import com.zdkj.uilib.anno.DialogPosition.Companion.POS_TOP


/**
 * 对话框位置
 */
@IntDef(value=[POS_CENTER, POS_BOTTOM, POS_TOP])
@Retention(value = AnnotationRetention.SOURCE)
@Target(allowedTargets = [AnnotationTarget.FIELD,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.VALUE_PARAMETER])
annotation class DialogPosition {
    companion object {
        const val POS_CENTER                    = 0
        const val POS_BOTTOM                    = 1
        const val POS_TOP                       = 2
    }
}
