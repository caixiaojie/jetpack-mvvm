package com.cxj.jetpackmvvm.ui.main

import android.content.Context
import android.content.Intent
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.databinding.ActivityMainBinding
import com.cxj.reacthttp.base.BaseVMActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseVMActivity() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            starter.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(starter)
        }
    }

    private val viewModel by viewModel<MainViewModel>()
    private val binding by binding<ActivityMainBinding>(R.layout.activity_main)


    override fun initView() {
        binding.run {
            binding.container.init(supportFragmentManager,viewModel)
        }
    }

    override fun initData() {
//        Thread{
//            val success = PhotoUtils.saveFile2Gallery(this,
//                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607946748024&di=cbc896d0350ac6d8ec39a5dcb750f887&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F52%2F62%2F31300542679117141195629117826.jpg")
//            runOnUiThread {
//                if (success) {
//                    toast("保存成功")
//                }else {
//                    toast("保存失败")
//                }
//            }
//        }.start()
    }

    override fun startObserve() {
    }

    override fun isDoubleClickExit(): Boolean  = true


}