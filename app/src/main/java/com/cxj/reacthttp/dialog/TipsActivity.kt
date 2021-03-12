package com.cxj.reacthttp.dialog

import android.graphics.Color
import android.graphics.Typeface
import android.os.Handler
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.databinding.ActivityTipsBinding
import com.cxj.reacthttp.base.BaseVMActivity
import com.zdkj.ktx.utils.ImageUtils
import com.zdkj.ktx.utils.ResUtils
import com.zdkj.uilib.anno.BottomButtonPosition
import com.zdkj.uilib.anno.BottomButtonStyle.Companion.BUTTON_STYLE_DOUBLE
import com.zdkj.uilib.anno.BottomButtonStyle.Companion.BUTTON_STYLE_TRIPLE
import com.zdkj.uilib.anno.DialogPosition
import com.zdkj.uilib.anno.LoadingStyle
import com.zdkj.uilib.bean.TextStyleBean
import com.zdkj.uilib.dialog.BeautyDialog
import com.zdkj.uilib.dialog.MessageDialog
import com.zdkj.uilib.dialog.content.AddressContent
import com.zdkj.uilib.dialog.content.SimpleEditor
import com.zdkj.uilib.dialog.footer.SimpleFooter
import com.zdkj.uilib.dialog.title.SimpleTitle
import com.zdkj.uilib.utils.UView

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/3/9
 *      des:
 * </pre>
 */
class TipsActivity : BaseVMActivity() {
    private val binding by binding<ActivityTipsBinding>(R.layout.activity_tips)
    override fun initView() {
        binding.run {

            btnLoading.setOnClickListener {
                val dlg = MessageDialog.showLoading(
                    this@TipsActivity,
                    "加载中...\n[2秒之后自动关闭]",
                    false)
                dlg.show()
                Handler().postDelayed({ MessageDialog.hide(dlg) }, 2000)
            }
            btnLoadingCustom.setOnClickListener {
                val dlg = MessageDialog.builder(
                    "抱歉，出错了!\n[2秒之后自动关闭]",
                    cancelable = false,
                    loading = false,
                    icon = ImageUtils.tintDrawable(R.drawable.uix_error_outline_black_24dp,
                        Color.WHITE)
                ).withMessageStyle(TextStyleBean().apply {
                    textColor = Color.WHITE
                    typeFace = Typeface.BOLD
                }).withBorderRadius(UView.dp2px(20f)).build(this@TipsActivity)
                dlg.show()
                Handler().postDelayed({ MessageDialog.hide(dlg) }, 2000)
            }
            btnLoadingCancelable.setOnClickListener {
                MessageDialog.showLoading(this@TipsActivity, "加载中...", true).show()
            }
            btnLoadingCancelableLong.setOnClickListener {
                MessageDialog.builder(
                    "君不見黃河之水天上來，奔流到海不復回。 君不見高堂明鏡悲白髮，朝如青絲暮成雪。 人生得意須盡歡，莫使金樽空對月。 天生我材必有用，千金散盡還復來。 烹羊宰牛且爲樂，會須一飲三百杯。 岑夫子，丹丘生。將進酒，杯莫停。 與君歌一曲，請君爲我側耳聽。 鐘鼓饌玉不足貴，但願長醉不願醒。 古來聖賢皆寂寞，惟有飲者留其名。 陳王昔時宴平樂，斗酒十千恣讙謔。 主人何為言少錢？徑須沽取對君酌。 五花馬，千金裘。呼兒將出換美酒，與爾同銷萬古愁。",
                    true,
                    loadingStyle = LoadingStyle.STYLE_ANDROID
                ).withMessageStyle(TextStyleBean().apply {
                    textSize = 14f
                }).build(this@TipsActivity).show()
            }

            btnEdit.setOnClickListener {
                BeautyDialog.Builder()
                    .setDialogTitle(SimpleTitle.Builder()
                        .setTitle("普通编辑对话框")
                        .build())
                    .setDialogContent(SimpleEditor.Builder()
                        .setClearDrawable(ResUtils.getDrawable(R.drawable.ic_cancel_black_24dp))
                        .build())
                    .setDialogBottom(SimpleFooter.Builder()
                        .setBottomStyle(BUTTON_STYLE_DOUBLE)
                        .setLeftText("Left")
//                        .setMiddleText("Middle")
                        .setRightText("Right")
                        .setOnClickListener { dialog, position, title, content ->
                            when(position) {

                            }
                            if (BottomButtonPosition.isRight(position)) {
                                dialog.dismiss()
                            }
                        }
                        .build())
                    .build().show(supportFragmentManager, "editor")
            }

            btnCityChoose.setOnClickListener {
                BeautyDialog.Builder()
                    .setDialogTitle(SimpleTitle.builder().setTitle("选择地区").build())
                    .setDialogContent(AddressContent.Builder()
                        .setOnAddressSelectedListener { dialog, province, city, area ->

                        }.build())
                    .setDialogBottom(SimpleFooter.Builder()
                        .setBottomStyle(BUTTON_STYLE_DOUBLE)
                        .setLeftText("取消")
                        .setRightText("确定")
                        .setOnClickListener { dialog, position, title, content ->

                        }
                        .build())
                    .setDialogPosition(DialogPosition.POS_BOTTOM)
                    .setDialogMargin(0)
                    .build().show(supportFragmentManager,"citySelector")
            }
        }
    }

    override fun initData() {
    }

    override fun startObserve() {
    }
}