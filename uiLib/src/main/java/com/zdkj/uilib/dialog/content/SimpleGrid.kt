package com.zdkj.uilib.dialog.content

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zdkj.uilib.databinding.UixDialogContentListSimpleBinding
import com.zdkj.uilib.dialog.BeautyDialog
import com.zdkj.uilib.rv.getAdapter

class SimpleGrid<T> private constructor(
        private val itemLayout:Int,
        private val converter: (helper: BaseViewHolder, item: T) -> Unit
): ViewBindingDialogContent<UixDialogContentListSimpleBinding>() {

    private lateinit var dialog: BeautyDialog
    private var itemClickListener: ((dialog: BeautyDialog, item: T) -> Unit)? = null
    private var list: List<T> = emptyList()
    private var spanCount = 4

    override fun doCreateView(ctx: Context) {
        val adapter = getAdapter(itemLayout, converter, list)
        binding.rv.adapter = adapter
        binding.rv.layoutManager = GridLayoutManager(ctx, spanCount)
        adapter.setOnItemClickListener { _, _, pos -> itemClickListener?.invoke(dialog, adapter.data[pos]) }
    }

    override fun setDialog(dialog: BeautyDialog) {
        this.dialog = dialog
    }

    class Builder<T> constructor(
        @LayoutRes val itemLayout:Int,
        private val converter: (helper: BaseViewHolder, item: T) -> Unit
    ) {
        private var list: List<T> = emptyList()
        private var itemClickListener: ((dialog: BeautyDialog, item: T) -> Unit)? = null
        private var spanCount = 4

        fun setSpanCount(spanCount: Int): Builder<T> {
            this.spanCount = spanCount
            return this
        }

        fun setList(list: List<T>): Builder<T> {
            this.list = list
            return this
        }

        fun setOnItemClickListener(itemClickListener: (dialog: BeautyDialog, item: T) -> Unit): Builder<T> {
            this.itemClickListener = itemClickListener
            return this
        }

        fun build(): SimpleGrid<T> {
            val simpleList = SimpleGrid(itemLayout, converter)
            simpleList.list = list
            simpleList.spanCount = spanCount
            simpleList.itemClickListener = itemClickListener
            return simpleList
        }
    }
}