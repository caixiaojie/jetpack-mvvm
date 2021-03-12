package com.cxj.jetpackmvvm.ui.login

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import com.cxj.jetpackmvvm.ui.main.MainActivity
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.base.BaseVMActivity
import com.cxj.jetpackmvvm.databinding.ActivityLoginBinding
import com.zdkj.ktx.ext.toast
import com.cxj.jetpackmvvm.model.bean.Title
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseVMActivity() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, LoginActivity::class.java)
            context.startActivity(starter)
        }
    }

    private val loginViewModel by viewModel<LoginViewModel>()
    private val binding by binding<ActivityLoginBinding>(R.layout.activity_login)

    override fun initView() {
        binding.run {
            viewModel = loginViewModel
            title =  Title(R.string.login, R.drawable.arrow_back) { onBackPressed() }
        }
    }

    override fun initData() {
    }

    @ExperimentalCoroutinesApi
    override fun startObserve() {
        loginViewModel.apply {
            uiState.observe(this@LoginActivity, Observer {
                if (it.isLoading) showProgressDialog() else dismissProgressDialog()

                it.isSuccess?.let {
                    dismissProgressDialog()
                    MainActivity.start(this@LoginActivity)
                    finish()
                }

                it.isError?.let { err ->
                    toast(err)
                }

                if (it.needLogin) loginViewModel.login()
            })
        }
    }

    private var progressDialog: ProgressDialog? = null
    private fun showProgressDialog() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        progressDialog?.show()
    }

    private fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }

}