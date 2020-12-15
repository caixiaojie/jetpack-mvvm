package com.cxj.jetpackmvvm.ui.profile

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.databinding.ItemProfileBinding
import com.cxj.jetpackmvvm.model.bean.ProfileItem

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/10
 *      des:
 * </pre>
 */
class ProfileAdapter : BaseQuickAdapter<ProfileItem,BaseDataBindingHolder<ItemProfileBinding>>(R.layout.item_profile) {
    override fun convert(holder: BaseDataBindingHolder<ItemProfileBinding>, item: ProfileItem) {
        holder.dataBinding?.run {
            profile = item
            executePendingBindings()
        }
    }
}