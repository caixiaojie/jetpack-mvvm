package com.cxj.jetpackmvvm.di

import com.cxj.jetpackmvvm.App
import com.cxj.jetpackmvvm.model.api.WanRetrofitClient
import com.cxj.jetpackmvvm.model.api.WanService
import com.cxj.jetpackmvvm.model.repository.HomeRepository
import com.cxj.jetpackmvvm.model.repository.LoginRepository
import com.cxj.jetpackmvvm.model.repository.ProjectRepository
import com.cxj.jetpackmvvm.room.WanDataBase
import com.cxj.jetpackmvvm.ui.home.HomeViewModel
import com.cxj.jetpackmvvm.ui.login.LoginViewModel
import com.cxj.jetpackmvvm.ui.main.MainViewModel
import com.cxj.jetpackmvvm.ui.project.ProjectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel() }
    viewModel { HomeViewModel(get()) }
    viewModel { ProjectViewModel(get()) }
}

val repositoryModule = module {
    single { WanRetrofitClient.getService(WanService::class.java, WanService.BASE_URL) }
    single { LoginRepository(get()) }
    single { HomeRepository(get(), WanDataBase.getDatabase(App.CONTEXT)) }
    single { ProjectRepository(get(), WanDataBase.getDatabase(App.CONTEXT)) }
}

val appModule = listOf(viewModelModule, repositoryModule)