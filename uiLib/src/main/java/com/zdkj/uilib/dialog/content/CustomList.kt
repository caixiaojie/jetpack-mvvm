package com.zdkj.uilib.dialog.content

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zdkj.uilib.databinding.UixDialogContentListCustomBinding
import com.zdkj.uilib.dialog.BeautyDialog
import com.zdkj.uilib.rv.EmptyView
import com.zdkj.uilib.rv.IEmptyView

/**
 * Custom list dialog content
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-21 13:59
 */
class CustomList private constructor(): ViewBindingDialogContent<UixDialogContentListCustomBinding>() {

    private lateinit var dialog: BeautyDialog

    private var emptyView: IEmptyView? = null
    private var adapter: RecyclerView.Adapter<*>? = null

    override fun doCreateView(ctx: Context) {
        emptyView?.getView()?.let {
            binding.flContainer.addView(it, ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT))
            binding.rv.setEmptyView(it)
        }
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(ctx)
    }

    override fun setDialog(dialog: BeautyDialog) {
        this.dialog = dialog
    }

    fun getDialog(): BeautyDialog = dialog

    fun showLoading() {
        emptyView?.show()
        emptyView?.showLoading()
    }

    fun showEmpty() {
        emptyView?.show()
        emptyView?.showEmpty()
    }

    fun hideEmptyView() {
        emptyView?.hide()
    }

    class Builder (context: Context) {
        private var emptyView: IEmptyView? = EmptyView.Builder(context).build()
        private var adapter: RecyclerView.Adapter<*>? = null

        fun setEmptyView(emptyView: IEmptyView): Builder {
            this.emptyView = emptyView
            return this
        }

        fun setAdapter(adapter: RecyclerView.Adapter<*>):Builder {
            this.adapter = adapter
            return this
        }

        fun build(): CustomList {
            val customList = CustomList()
            customList.adapter = adapter
            customList.emptyView = emptyView
            return customList
        }
    }
}