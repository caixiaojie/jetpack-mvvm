package com.cxj.jetpackmvvm.di

import com.cxj.jetpackmvvm.model.api.WanRetrofitClient
import com.cxj.jetpackmvvm.model.api.WanService
import com.cxj.jetpackmvvm.model.repository.LoginRepository
import com.cxj.jetpackmvvm.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
}

val repositoryModule = module {
    single { WanRetrofitClient.getService(WanService::class.java, WanService.BASE_URL) }
    single { LoginRepository(get()) }
}

val appModule = listOf(viewModelModule, repositoryModule)