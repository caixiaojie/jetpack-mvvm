package com.cxj.jetpackmvvm.ui.article

import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.base.BaseVMActivity
import com.cxj.jetpackmvvm.databinding.ActivityArticleLayoutBinding
import com.zdkj.ktx.ext.toast
import com.cxj.jetpackmvvm.util.WaterMarkUtil
import com.gyf.immersionbar.ImmersionBar
import com.qmuiteam.qmui.skin.QMUISkinManager
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet.BottomGridSheetBuilder
import kotlinx.android.synthetic.main.activity_article_layout.*

/**
 * <pre>
 *      author: 二哈
 *      date: 2020/12/14
 *      des:
 * </pre>
 */
const val PAGE_NAME = "PAGE_NAME"
const val PAGE_URL = "PAGE_URL"
const val PAGE_ID = "PAGE_ID"
const val ORIGIN_ID = "ORIGIN_ID"
const val USER_ID = "USER_ID"
const val IS_COLLECTION = "IS_COLLECTION"

class ArticleActivity : BaseVMActivity() {
    private val binding by binding<ActivityArticleLayoutBinding>(R.layout.activity_article_layout)
    private var pageName = ""
    private var pageUrl = ""
    private var pageId = -1
    private var originId = -1
    private var userId = -1
    private var isCollection = -1

    override fun initImmersionBar() {
        super.initImmersionBar()
        ImmersionBar.with(this).statusBarDarkFont(false).titleBar(toolbar).init()
    }
    override fun initView() {
        binding.run {
            WaterMarkUtil.showWatermarkView(this@ArticleActivity, "Hello Word!")
            articleImgRight.setOnClickListener {
                showSimpleBottomSheetGrid()
            }
        }
    }

    override fun initData() {
        pageName = intent.getStringExtra(PAGE_NAME) ?: ""
        pageUrl = intent.getStringExtra(PAGE_URL) ?: ""
        pageId = intent.getIntExtra(PAGE_ID, -1)
        isCollection = intent.getIntExtra(IS_COLLECTION, -1)
        originId = intent.getIntExtra(ORIGIN_ID, -1)
        userId = intent.getIntExtra(USER_ID, -1)
        articleTxtTitle.text = pageName
        binding.mWebView.loadUrl(pageUrl)
    }

    override fun startObserve() {
    }

    private fun showSimpleBottomSheetGrid() {
        val TAG_SHARE_WECHAT_FRIEND = 0
        val TAG_SHARE_WECHAT_MOMENT = 1
        val TAG_SHARE_WEIBO = 2
        val TAG_SHARE_CHAT = 3
        val TAG_SHARE_LOCAL = 4
        val builder = BottomGridSheetBuilder(this)
            .addItem(R.mipmap.icon_more_operation_share_friend, "分享到微信", TAG_SHARE_WECHAT_FRIEND, BottomGridSheetBuilder.FIRST_LINE)
            .addItem(R.mipmap.icon_more_operation_share_moment, "分享到朋友圈", TAG_SHARE_WECHAT_MOMENT, BottomGridSheetBuilder.FIRST_LINE)
            .addItem(R.mipmap.icon_more_operation_share_weibo, "分享到微博", TAG_SHARE_WEIBO, BottomGridSheetBuilder.FIRST_LINE)
            .addItem(R.mipmap.icon_more_operation_share_chat, "分享到私信", TAG_SHARE_CHAT, BottomGridSheetBuilder.FIRST_LINE)
            .addItem(R.mipmap.icon_more_operation_save, "保存到本地", TAG_SHARE_LOCAL, BottomGridSheetBuilder.SECOND_LINE)
            .setAddCancelBtn(true)
            .setSkinManager(QMUISkinManager.defaultInstance(this))
            .setOnSheetItemClickListener { dialog, itemView ->
                dialog.dismiss()
                when (itemView.tag as Int) {
                    TAG_SHARE_WECHAT_FRIEND ->
                    toast("分享到微信")
                    TAG_SHARE_WECHAT_MOMENT ->
                    toast("分享到朋友圈")
                    TAG_SHARE_WEIBO ->
                    toast("分享到微博")
                    TAG_SHARE_CHAT ->
                    toast("分享到私信")
                    TAG_SHARE_LOCAL ->
                    toast("保存到本地")
                }
            }.build().show()
    }
}