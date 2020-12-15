package com.cxj.jetpackmvvm.ui.welcome

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.base.BaseVMActivity
import com.cxj.jetpackmvvm.databinding.ActivityWelcomeBinding
import com.cxj.jetpackmvvm.ui.login.LoginActivity
import com.cxj.jetpackmvvm.ui.main.MainActivity
import com.cxj.jetpackmvvm.util.MMkvHelper
import com.cxj.jetpackmvvm.widget.CountdownView
import com.cxj.jetpackmvvm.widget.CountdownView.CountdownEndListener


/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/8
 *      des:
 * </pre>
 */
class WelcomeActivity : BaseVMActivity() {
    private val binding by binding<ActivityWelcomeBinding>(R.layout.activity_welcome)
    private val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val isLogin: Boolean = msg.obj as Boolean
//            jump(isLogin)
        }
    }

    private fun jump(/*isLogin: Boolean*/) {
        val login = MMkvHelper.getInstance().isLogin
        if (login) {
            MainActivity.start(this@WelcomeActivity)
        } else {
            LoginActivity.start(this@WelcomeActivity)
        }
        finish()
    }


    override fun initView() {
        binding.run {
            mCountDown.setOnClickListener {
                mCountDown.timeEnd()
                jump()
            }

            mCountDown.setMaxTime(5) //倒计时时间，单位秒
                .setConcatStr("s跳过") //倒计时后面拼接的字符串
                .setBgStyle(CountdownView.BgStyle.FILL) //背景样式 LINE：线  FILL:填充  CLEAR:不需要通过代码画背景
                .setBgColor(Color.WHITE) //要画背景的颜色值
                .setBgCorner(resources.getDimension(R.dimen.dp_20).toInt()) //要画背景的圆角值 单位dp
                .setEndListener { //倒计时结束，去吧皮卡丘
                    jump()
                }
                .timeStart() //开始倒计时

        }

    }

    override fun initData() {
    }

    override fun startObserve() {
    }

}