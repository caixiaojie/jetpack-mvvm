package com.zdkj.uilib.dialog.content

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.LinearLayoutManager
import com.zdkj.uilib.R
import com.zdkj.uilib.bean.TextStyleBean
import com.zdkj.uilib.databinding.UixDialogContentListSimpleBinding
import com.zdkj.uilib.dialog.BeautyDialog
import com.zdkj.uilib.rv.getAdapter
import com.zdkj.uilib.rv.goneIf
import com.zdkj.uilib.text.NormalTextView

/**
 * Simple list dialog content
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-15 19:06
 */
class SimpleList private constructor(): ViewBindingDialogContent<UixDialogContentListSimpleBinding>() {

    private lateinit var dialog: BeautyDialog

    private var list: List<Item> = emptyList()
    private var showIcon = true
    private var itemClickListener: ((dialog: BeautyDialog, item: Item) -> Unit)? = null
    private var textStyle = GlobalConfig.textStyle

    override fun doCreateView(ctx: Context) {
        val adapter = getAdapter(R.layout.uix_dialog_content_list_simple_item, { helper, item ->
            val tv = helper.getView<NormalTextView>(R.id.tv)
            tv.text = item.content
            tv.setStyle(textStyle, GlobalConfig.textStyle)
            item.gravity?.let { tv.gravity = it }
            item.icon?.let { helper.setImageDrawable(R.id.iv, it) }
            helper.goneIf(R.id.iv, !showIcon)
        }, list)
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(ctx)
        adapter.setOnItemClickListener { _, _, pos -> itemClickListener?.invoke(dialog, adapter.data[pos]) }
    }

    override fun setDialog(dialog: BeautyDialog) {
        this.dialog = dialog
    }

    class Builder {
        private var list: List<Item> = emptyList()
        private var showIcon = true
        private var textStyle = GlobalConfig.textStyle
        private var itemClickListener: ((dialog: BeautyDialog, item: Item) -> Unit)? = null

        fun setList(list: List<Item>): Builder {
            this.list = list
            return this
        }

        fun setTextStyle(textStyle: TextStyleBean): Builder {
            this.textStyle = textStyle
            return this
        }

        fun setShowIcon(showIcon: Boolean): Builder {
            this.showIcon = showIcon
            return this
        }

        fun setOnItemClickListener(itemClickListener: (dialog: BeautyDialog, item: Item) -> Unit): Builder {
            this.itemClickListener = itemClickListener
            return this
        }

        fun build(): SimpleList {
            val simpleList = SimpleList()
            simpleList.list = list
            simpleList.textStyle = textStyle
            simpleList.showIcon = showIcon
            simpleList.itemClickListener = itemClickListener
            return simpleList
        }
    }

    object GlobalConfig {
        var textStyle = TextStyleBean()
    }

    data class Item(val id: Int, var content: CharSequence?, var icon: Drawable?, var gravity: Int? = null)

    companion object {

        fun get(items: List<Item>): SimpleList = Builder().setList(items).build()

        fun builder(): Builder = Builder()
    }
}