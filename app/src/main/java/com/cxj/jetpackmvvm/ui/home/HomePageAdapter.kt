package com.cxj.jetpackmvvm.ui.home

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.databinding.ItemHomeListBinding
import com.cxj.jetpackmvvm.model.bean.Article

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/9
 *      des:
 * </pre>
 */
class HomePageAdapter : BaseQuickAdapter<Article,BaseDataBindingHolder<ItemHomeListBinding>>(R.layout.item_home_list) {
    override fun convert(holder: BaseDataBindingHolder<ItemHomeListBinding>, item: Article) {
        holder.dataBinding?.run {
            article = item
            executePendingBindings()
        }
    }
}