package com.cxj.jetpackmvvm.ui.main

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.widget.BaseHomeBottomTabWidget

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/8
 *      des:
 * </pre>
 */
class HomeBottomTabWidget @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : BaseHomeBottomTabWidget(context, attrs, defStyleAttr, R.layout.layout_home_bottom_tab) {

    private var textViews: ArrayList<TextView>? = null

    override fun initView(view: View) {
        textViews = arrayListOf(
            view.findViewById(R.id.llHomeATHome),
            view.findViewById(R.id.llHomeATCalendar),
            view.findViewById(R.id.llHomeATObject),
            view.findViewById(R.id.llHomeATMy)
        )
        for (textView in textViews!!) {
            textView.setOnClickListener(this)
        }
    }

    override fun destroy() {
        super.destroy()
        if (!textViews.isNullOrEmpty()) {
            textViews!!.clear()
            textViews = null
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.llHomeATHome -> fragmentManger(0)
            R.id.llHomeATCalendar -> fragmentManger(1)
            R.id.llHomeATObject -> fragmentManger(2)
            R.id.llHomeATMy -> fragmentManger(3)
        }
    }
    /**
     * fragment的切换 实现底部导航栏的切换
     *
     * @param position 序号
     */
    override fun fragmentManger(position: Int) {
        super.fragmentManger(position)
        for (j in textViews!!.indices) {
            textViews!![j].isSelected = position == j
        }
    }
}