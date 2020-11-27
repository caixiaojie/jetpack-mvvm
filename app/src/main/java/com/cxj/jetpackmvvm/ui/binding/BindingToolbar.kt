package com.cxj.jetpackmvvm.ui.binding

import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter

@BindingAdapter("title", "icon", "navigationClick", requireAll = false)
fun Toolbar.init(titleResId: Int, iconResId: Int, action: () -> Unit) {
    setTitle(titleResId)
    setNavigationIcon(iconResId)
    setNavigationOnClickListener { action() }
}