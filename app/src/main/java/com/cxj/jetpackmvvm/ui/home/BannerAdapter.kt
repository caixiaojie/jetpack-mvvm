package com.cxj.jetpackmvvm.ui.home

import android.view.View
import android.view.ViewGroup
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.model.bean.BannerBean
import com.zhpan.bannerview.BaseBannerAdapter

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/10
 *      des:
 * </pre>
 */
class BannerAdapter : BaseBannerAdapter<BannerBean,BannerViewHolder>() {
    override fun createViewHolder(
        parent: ViewGroup,
        itemView: View?,
        viewType: Int
    ): BannerViewHolder {
        return BannerViewHolder(itemView!!)
    }

    override fun onBind(
        holder: BannerViewHolder?,
        data: BannerBean?,
        position: Int,
        pageSize: Int
    ) {
        holder?.bindData(data, position, pageSize)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_banner
    }
}