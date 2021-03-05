package com.cxj.reacthttp.base

import com.cxj.reacthttp.http.SelfRemoteDataSource
import com.zdkj.reacthttp.base.BaseReactiveViewModel

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/3/5
 *      des:
 * </pre>
 */
open class BaseViewModel : BaseReactiveViewModel() {
    /**
     * 正常来说单个项目中应该只有一个 RemoteDataSource 实现类，即全局使用同一份配置
     * 但父类也应该允许子类使用一个单独的 RemoteDataSource
     */
    protected open val remoteDataSource by lazy {
        SelfRemoteDataSource(this)
    }
}