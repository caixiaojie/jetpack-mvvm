package com.zdkj.uilib.bean

import androidx.annotation.ColorInt
import androidx.annotation.Size
import java.io.Serializable

data class TextStyleBean(
        @ColorInt
        var textColor: Int?                 = null,
        @Size
        var textSize: Float?                = null,
        var typeFace: Int?                  = null,
        var gravity: Int?                   = null
): Serializable