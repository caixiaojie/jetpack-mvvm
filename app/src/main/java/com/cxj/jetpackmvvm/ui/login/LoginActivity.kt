package com.cxj.jetpackmvvm.ui.login

import android.app.ProgressDialog
import androidx.lifecycle.Observer
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.base.BaseVMActivity
import com.cxj.jetpackmvvm.databinding.ActivityLoginBinding
import com.cxj.jetpackmvvm.ext.toast
import com.cxj.jetpackmvvm.model.bean.Title
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseVMActivity() {

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