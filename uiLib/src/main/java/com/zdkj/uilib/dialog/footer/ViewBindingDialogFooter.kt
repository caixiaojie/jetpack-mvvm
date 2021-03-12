package com.zdkj.uilib.dialog.footer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.zdkj.ktx.ext.loge
import java.lang.reflect.ParameterizedType

abstract class ViewBindingDialogFooter<T : ViewBinding> : IDialogFooter {

    protected lateinit var binding: T
        private set

    override fun getView(ctx: Context): View {
        val vbClass: Class<T> = ((this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments)
                .firstOrNull { ViewBinding::class.java.isAssignableFrom(it as Class<*>) } as? Class<T>
                ?: throw IllegalStateException("You must specify a view binding class.")
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        try {
            binding = method.invoke(null, LayoutInflater.from(ctx)) as T
            doCreateView(ctx)
        } catch (e: Exception) {
            ("Failed to inflate view binding.").loge()
        }
        return binding.root
    }

    abstract fun doCreateView(ctx: Context)
}
