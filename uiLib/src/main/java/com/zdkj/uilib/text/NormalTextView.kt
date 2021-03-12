package com.zdkj.uilib.text

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.zdkj.uilib.bean.TextStyleBean

/**
 * 普通等文本控件，与 [AppCompatTextView] 不同的是支持通过 [TextStyleBean] 设置文字风格。
 */
class NormalTextView : AppCompatTextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * 设置文字风格
     */
    fun setStyle(style: TextStyleBean) {
        if (style.textSize != null) this.textSize = style.textSize!!
        if (style.gravity != null) this.gravity = style.gravity!!
        if (style.textColor != null) setTextColor(style.textColor!!)
        if (style.typeFace != null) this.setTypeface(null, style.typeFace!!)
    }

    /**
     * 设置文字风格 允许设置两个文字风格，优先使用 [self] 指定的属性，当 [self] 指定的属性不存在
     * 的时候使用 [global] 指定的属性。这个是提供给 SDK 内部的一些控件使用的便利方法。
     *
     * @param self 文字的风格
     * @param global 全局的文字风格
     */
    fun setStyle(self: TextStyleBean, global: TextStyleBean) {
        if (self.textSize != null) {
            this.textSize = self.textSize!!
        } else if (global.textSize != null) {
            this.textSize = global.textSize!!
        }
        if (self.gravity != null) {
            this.gravity = self.gravity!!
        } else if (global.gravity != null) {
            this.gravity = global.gravity!!
        }
        if (self.textColor != null) {
            setTextColor(self.textColor!!)
        } else if (global.textColor != null) {
            setTextColor(global.textColor!!)
        }
        if (self.typeFace != null) {
            this.setTypeface(null, self.typeFace!!)
        } else if (global.typeFace != null) {
            this.setTypeface(null, global.typeFace!!)
        }
    }
}
