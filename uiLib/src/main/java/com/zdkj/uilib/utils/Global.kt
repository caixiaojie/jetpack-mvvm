package com.zdkj.uilib.utils

import android.app.Activity
import android.view.View

fun <T : View> Activity.f(id: Int): T = findViewById<T>(id)
