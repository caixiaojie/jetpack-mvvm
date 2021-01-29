package com.cxj.jetpackmvvm.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/1/29
 *      des:
 * </pre>
 */
class RecyclerViewAtViewPager2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    private var disallowIntercept = false

    private var startX = 0
    private var startY = 0
    var isDispatch: Boolean = true
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (isDispatch) {
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = ev.x.toInt()
                    startY = ev.y.toInt()
                    parent.requestDisallowInterceptTouchEvent(true)
                }
                MotionEvent.ACTION_MOVE -> {
                    val endX = ev.x.toInt()
                    val endY = ev.y.toInt()
                    val disX = abs(endX - startX)
                    val disY = abs(endY - startY)
                    if (disX > disY) {
                        //为了解决RecyclerView嵌套RecyclerView时横向滑动的问题
                        if (disallowIntercept) {
                            parent.requestDisallowInterceptTouchEvent(disallowIntercept)
                        } else {
                            parent.requestDisallowInterceptTouchEvent(canScrollHorizontally(startX - endX))
                        }
                    } else {
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> parent.requestDisallowInterceptTouchEvent(
                    false
                )
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        this.disallowIntercept = disallowIntercept
        super.requestDisallowInterceptTouchEvent(disallowIntercept)

    }


}