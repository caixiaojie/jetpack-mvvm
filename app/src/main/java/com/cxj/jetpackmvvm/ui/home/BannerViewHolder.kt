package com.cxj.jetpackmvvm.ui.home

import android.view.View
import android.widget.ImageView
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.model.bean.BannerBean
import com.cxj.jetpackmvvm.ui.binding.bindingImage
import com.zhpan.bannerview.BaseViewHolder

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/10
 *      des:
 * </pre>
 */
class BannerViewHolder(itemView: View) : BaseViewHolder<BannerBean>(itemView) {
    override fun bindData(data: BannerBean?, position: Int, pageSize: Int) {
        val bannerImg = itemView.findViewById<ImageView>(R.id.banner_image)
        data?.let {
            bindingImage(bannerImg,data.imagePath)
        }

    }
}