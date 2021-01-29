package com.hi.dhl.plugin

/**
 * <pre>
 *     author: dhl
 *     date  : 2020/6/15
 *     desc  : 如果数量少的话，放在一个类里面就可以，如果数量多的话，可以拆分为多个类
 * </pre>
 */

object Versions {
    const val persistentCookieJar = "v1.0.1"
    const val retrofit = "2.9.0"
    const val okhttpLogging = "4.8.0"
    const val appcompat = "1.2.0"
    const val coreKtx = "1.3.2"
    const val constraintlayout = "2.0.4"
    const val paging = "3.0.0-alpha01"
    const val timber = "4.7.1"
    const val kotlin = "1.4.0"
    const val koin = "2.1.5"
    const val work = "2.2.0"
    const val room = "2.2.5"
    const val cardview = "1.0.0"
    const val recyclerview = "1.0.0"
    const val fragment = "1.2.1"
    const val navigation = "2.3.1"
    const val anko = "0.10.8"
    const val material = "1.2.1"
    const val lifecycle = "2.2.0"
    const val coil = "1.1.0"
    const val mmkv = "1.2.6"

    const val junit = "4.12"
    const val junitExt = "1.1.2"
    const val espressoCore = "3.3.0"
    const val jDatabinding = "1.0.1"
    const val qmui = "2.0.0-alpha11"
    const val adapter = "3.0.4"
    const val smartRefresh = "2.0.1"
    const val banner = "3.2.0"
    const val immersionbar = "3.0.0"
    const val lifecycle_version = "2.2.0"
}

object AndroidX {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val pagingRuntime = "androidx.paging:paging-runtime:${Versions.paging}"

    const val workRuntime = "androidx.work:work-runtime:${Versions.work}"
    const val workTesting = "androidx.work:work-testing:${Versions.work}"
    const val cardview = "androidx.cardview:cardview:${Versions.cardview}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle_version}"
    const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_version}"
    const val lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}"
    const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle_version}"
    //api "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
    //    api "androidx.lifecycle:lifecycle-runtime-ktx:2.2.0"
    //    api "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.room}"
    const val compiler = "androidx.room:room-compiler:${Versions.room}"
    const val ktx = "androidx.room:room-ktx:${Versions.room}"
    const val rxjava2 = "androidx.room:room-rxjava2:${Versions.room}"
    const val testing = "androidx.room:room-testing:${Versions.room}"
}

object Fragment {
    const val navigation_fragment = "androidx.navigation:navigation-fragment:${Versions.navigation}"
    const val navigation_ui = "androidx.navigation:navigation-ui:${Versions.navigation}"
    const val navigation_fragment_ktx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigation_ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val runtime = "androidx.fragment:fragment:${Versions.fragment}"
    const val runtimeKtx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val testing = "androidx.fragment:fragment-testing:${Versions.fragment}"
}

object Kt {
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val stdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val stdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val test = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object Koin {
    const val core = "org.koin:koin-core:${Versions.koin}"
    const val androidCore = "org.koin:koin-android:${Versions.koin}"
    const val viewmodel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    const val androidScope = "org.koin:koin-android-scope:${Versions.koin}"
}

object Anko {
    const val common = "org.jetbrains.anko:anko-common:${Versions.anko}"
    const val sqlite = "org.jetbrains.anko:anko-sqlite:${Versions.anko}"
    const val anko_coroutines = "org.jetbrains.anko:anko-coroutines:${Versions.anko}"
    const val design = "org.jetbrains.anko:anko-design:${Versions.anko}" // For SnackBars
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val mock = "com.squareup.retrofit2:retrofit-mock:${Versions.retrofit}"
    const val logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLogging}"
    const val persistentCookieJar = "com.github.franmontiel:PersistentCookieJar:${Versions.persistentCookieJar}"
}
//加载图片框架
object Coil {
    const val coil = "io.coil-kt:coil:${Versions.coil}"
}

object CymChad {
    const val adapter = "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.adapter}"
}

object Material {
    const val material = "com.google.android.material:material:${Versions.material}"
}

object Lifecycle {
    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
}

object X5WebView {
    const val x5WebView = "com.tencent.tbs.tbssdk:sdk:43939"
}

object Banner {
    const val banner = "com.github.zhpanvip:BannerViewPager:${Versions.banner}"
}

object Tencent {
    const val mmkv = "com.tencent:mmkv-static:${Versions.mmkv}"
    const val qmui = "com.qmuiteam:qmui:${Versions.qmui}"
    const val bugly = "com.tencent.bugly:crashreport:latest.release"
}

object Immersion {
    const val immersionbar = "com.gyf.immersionbar:immersionbar:${Versions.immersionbar}"
    const val immersionbar_components = "com.gyf.immersionbar:immersionbar-components:${Versions.immersionbar}"
    const val immersionbar_ktx = "com.gyf.immersionbar:immersionbar-ktx:${Versions.immersionbar}"
}

object SmartRefresh {
    const val refresh_layout_kernel = "com.scwang.smart:refresh-layout-kernel:${Versions.smartRefresh}"//核心必须依赖
    const val refresh_header_classics = "com.scwang.smart:refresh-header-classics:${Versions.smartRefresh}"//经典刷新头
    const val refresh_header_radar = "com.scwang.smart:refresh-header-radar:${Versions.smartRefresh}"//雷达刷新头
    const val refresh_header_falsify = "com.scwang.smart:refresh-header-falsify:${Versions.smartRefresh}"//虚拟刷新头
    const val refresh_header_material = "com.scwang.smart:refresh-header-material:${Versions.smartRefresh}"//谷歌刷新头
    const val refresh_header_two_level = "com.scwang.smart:refresh-header-two-level:${Versions.smartRefresh}"//二级刷新头
    const val refresh_footer_ball = "com.scwang.smart:refresh-footer-ball:${Versions.smartRefresh}"//球脉冲加载
    const val refresh_footer_classics = "com.scwang.smart:refresh-footer-classics:${Versions.smartRefresh}"//经典加载
}

object Depend {

    const val junit = "junit:junit:${Versions.junit}"
    const val androidTestJunit = "androidx.test.ext:junit:${Versions.junitExt}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val jDatabinding = "com.hi-dhl:jdatabinding:${Versions.jDatabinding}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}

