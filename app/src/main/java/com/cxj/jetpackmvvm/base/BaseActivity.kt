package com.cxj.jetpackmvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cxj.jetpackmvvm.util.DoubleClickExitDetector


abstract class BaseActivity : AppCompatActivity() {
    private lateinit var doubleClickExitDetector: DoubleClickExitDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doubleClickExitDetector = DoubleClickExitDetector(this, "再按一次退出", 2000)
        setContentView(getLayoutResId())
        initView()
        initData()
    }

    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initData()
    open fun isDoubleClickExit(): Boolean {
        return false
    }

    override fun onBackPressed() {
        if (isDoubleClickExit()) {
            val isExit = doubleClickExitDetector.click()
            if (isExit) {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

}