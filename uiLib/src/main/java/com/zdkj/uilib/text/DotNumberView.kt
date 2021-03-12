package com.zdkj.uilib.text

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import com.zdkj.ktx.ext.dp
import com.zdkj.ktx.utils.ImageUtils
import kotlin.math.max

class DotNumberView : AppCompatTextView {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr)

    fun showNumber(num: String, @ColorInt circleColor: Int) {
        text = num
        background = ImageUtils.getDrawable(circleColor, 30f.dp().toFloat())
        post {
            val size = max(width, height)
            layoutParams = layoutParams.apply {
                width = size
                height = size
            }
            requestLayout()
        }
    }
}