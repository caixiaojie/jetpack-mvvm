package com.cxj.reacthttp.dialog

import android.graphics.Color
import android.graphics.Typeface
import android.os.Handler
import android.view.Gravity
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.databinding.ActivityDialogBinding
import com.cxj.jetpackmvvm.databinding.ActivityDialogBindingImpl
import com.cxj.jetpackmvvm.databinding.TestBinding
import com.cxj.reacthttp.base.BaseVMActivity
import com.zdkj.ktx.ext.toast
import com.zdkj.ktx.utils.ResUtils
import com.zdkj.ktx.utils.ViewUtils
import com.zdkj.ktx.utils.onDebouncedClick
import com.zdkj.uilib.anno.AddressSelectLevel.Companion.LEVEL_AREA
import com.zdkj.uilib.anno.BottomButtonPosition.Companion.isLeft
import com.zdkj.uilib.anno.BottomButtonPosition.Companion.isMiddle
import com.zdkj.uilib.anno.BottomButtonPosition.Companion.isRight
import com.zdkj.uilib.anno.BottomButtonStyle.Companion.BUTTON_STYLE_DOUBLE
import com.zdkj.uilib.anno.BottomButtonStyle.Companion.BUTTON_STYLE_SINGLE
import com.zdkj.uilib.anno.BottomButtonStyle.Companion.BUTTON_STYLE_TRIPLE
import com.zdkj.uilib.anno.DialogPosition.Companion.POS_BOTTOM
import com.zdkj.uilib.anno.DialogPosition.Companion.POS_TOP
import com.zdkj.uilib.anno.DialogStyle.Companion.STYLE_WRAP
import com.zdkj.uilib.anno.EmptyViewState
import com.zdkj.uilib.anno.LoadingStyle
import com.zdkj.uilib.bean.TextStyleBean
import com.zdkj.uilib.dialog.BeautyDialog
import com.zdkj.uilib.dialog.content.*
import com.zdkj.uilib.dialog.footer.SimpleFooter
import com.zdkj.uilib.dialog.title.SimpleTitle
import com.zdkj.uilib.image.CircleImageView
import com.zdkj.uilib.rate.RatingManager
import com.zdkj.uilib.rv.EmptyView
import com.zdkj.uilib.rv.getAdapter
import com.zdkj.uilib.rv.onItemDebouncedClick
import com.zdkj.uilib.text.NormalTextView
import com.zdkj.uilib.utils.UView

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/3/9
 *      des:
 * </pre>
 */
class DialogActivity : BaseVMActivity() {
    private val binding by binding<ActivityDialogBinding>(R.layout.activity_dialog)
    private var customList: CustomList? = null
    override fun initView() {
        binding.run {
            btnNoBg.setOnClickListener {
                BeautyDialog.Builder()
                    .onDismiss { toast("Dismissed") }
                    .onShow { toast("Showed") }
                    .setDialogStyle(STYLE_WRAP)
                    .setDialogBackground(null)
                    .setDialogContent(UpgradeContent())
                    .build().show(supportFragmentManager, "normal")
            }
            btnNormal.setOnClickListener {
                BeautyDialog.Builder()
                    .setDialogStyle(STYLE_WRAP)
                    .setDialogTitle(SimpleTitle.Builder()
                        .setTitle("测试标题 [RED|18f]")
                        .setTitleStyle(TextStyleBean().apply {
                            textSize = 18f
                            textColor = Color.RED
                        })
                        .build())
                    .setDialogContent(MultipleContent())
                    .setDialogBottom(SimpleFooter.Builder()
                        .setBottomStyle(BUTTON_STYLE_SINGLE)
                        .setMiddleText("OK")
                        .setMiddleTextStyle(TextStyleBean().apply {
                            textColor = Color.RED
                            typeFace = Typeface.BOLD
                        })
                        .setOnClickListener { dialog, _, _, _ ->
                            dialog.dismiss()
                        }.build())
                    .build().show(supportFragmentManager, "normal")
            }
            btnNormalTop.setOnClickListener {
                BeautyDialog.Builder()
                    .setDialogTitle(SimpleTitle.Builder()
                        .setTitle("测试标题 [BOLD|LEFT]")
                        .setTitleStyle(TextStyleBean().apply {
                            gravity = Gravity.START
                            typeFace = Typeface.BOLD
                        })
                        .build())
                    .setDialogContent(MultipleContent())
                    .setDialogBottom(SampleFooter())
                    .setDialogPosition(POS_TOP)
                    .build().show(supportFragmentManager, "normal_top")
            }
            btnNormalBottom.setOnClickListener {
                BeautyDialog.Builder()
                    .setDarkDialog(true)
                    .setDialogTitle(SimpleTitle.builder()
                        .setTitle("测试标题 [WHITE]")
                        .setTitleStyle(TextStyleBean().apply {
                            textColor = Color.WHITE
                        })
                        .build())
                    .setDialogContent(MultipleContent())
                    .setDialogBottom(SimpleFooter.Builder()
                        .setBottomStyle(BUTTON_STYLE_TRIPLE)
                        .setLeftText("左")
                        .setLeftTextStyle(TextStyleBean().apply {
                            textColor = Color.WHITE
                            textSize = 14f
                        })
                        .setMiddleText("中")
                        .setMiddleTextStyle(TextStyleBean().apply {
                            textColor = Color.WHITE
                            textSize = 15f
                        })
                        .setRightText("右")
                        .setRightTextStyle(TextStyleBean().apply {
                            textColor = Color.WHITE
                            textSize = 16f
                        })
                        .build())
                    .setDialogPosition(POS_BOTTOM)
                    .build().show(supportFragmentManager, "normal_bottom")
            }
            btnEditorNormal.setOnClickListener {
                BeautyDialog.Builder()
                    .setDialogCornerRadius(UView.dp2px(4f))
                    .setDialogTitle(SimpleTitle.get("普通编辑对话框 [无限制]"))
                    .setDialogContent(SimpleEditor.Builder()
                        .setClearDrawable(ResUtils.getDrawable(R.drawable.ic_cancel_black_24dp))
                        .build())
                    .setDialogBottom(SimpleFooter.Builder()
                        .setBottomStyle(BUTTON_STYLE_TRIPLE)
                        .setLeftText("Left")
                        .setMiddleText("Middle")
                        .setRightText("Right")
                        .setOnClickListener { _, p, _, _ ->
                            when {
                                isLeft(p) -> {
                                    toast("Left")
                                }
                                isRight(p) -> {
                                    toast("Right")
                                }
                                isMiddle(p) -> {
                                    toast("Middle")
                                }
                            }
                        }
                        .build())
                    .build().show(supportFragmentManager, "editor")
            }
            btnEditorNumeric.setOnClickListener {
                BeautyDialog.Builder()
                    .setDialogTitle(SimpleTitle.get("编辑对话框（数字|单行|长度10）"))
                    .setDialogContent(SimpleEditor.Builder()
                        .setSingleLine(true)
                        .setNumeric(true)
                        .setContent("10086")
                        .setHint("在这里输入数字...")
                        .setBottomLineColor(Color.LTGRAY)
                        .setMaxLength(10)
                        .build())
                    .setDialogBottom(SimpleFooter.Builder()
                        .setBottomStyle(BUTTON_STYLE_DOUBLE)
                        .setLeftText("取消")
                        .setRightText("确定")
                        .setDividerColor(Color.LTGRAY)
                        .setRightTextStyle(TextStyleBean().apply {
                            textColor = Color.GRAY
                        })
                        .setRightTextStyle(TextStyleBean().apply {
                            textColor = Color.RED
                        })
                        .setOnClickListener { dialog, position, _, content ->
                            if (isLeft(position)) dialog.dismiss()
                            else if (isRight(position)) {
                                toast((content as SimpleEditor).getContent().toString())
                            }
                        }.build())
                    .build().show(supportFragmentManager, "editor")
            }
            btnListNormal.setOnClickListener {
                BeautyDialog.Builder()
                    .setDialogPosition(POS_BOTTOM)
                    .setDialogTitle(SimpleTitle.get("简单的列表"))
                    .setDialogContent(SimpleList.Builder()
                        .setTextStyle(TextStyleBean().apply {
                            gravity = Gravity.CENTER
                            textSize = 14f
                            textColor = Color.BLACK
                            typeFace = Typeface.BOLD
                        })
                        .setShowIcon(true)
                        .setList(listOf(
                            SimpleList.Item(0, "秦时明月汉时关，万里长征人未还。\n" +
                                    "但使龙城飞将在，不教胡马度阴山。",
                                ResUtils.getDrawable(R.drawable.uix_eye_close_48)),
                            SimpleList.Item(1, "春眠不觉晓，处处闻啼鸟。\n" +
                                    "夜来风雨声，花落知多少。",
                                ResUtils.getDrawable(R.drawable.uix_eye_open_48)),
                            SimpleList.Item(2, "君自故乡来，应知故乡事。\n" +
                                    "来日绮窗前，寒梅著花未？",
                                ResUtils.getDrawable(R.drawable.uix_close_black_24dp),
                                Gravity.START),
                            SimpleList.Item(3, "松下问童子，言师采药去。\n" +
                                    "只在此山中，云深不知处。",
                                ResUtils.getDrawable(R.drawable.uix_loading),
                                Gravity.END)
                        ))
                        .setOnItemClickListener { dialog, item ->
                            toast("${item.id} : ${item.content}")
                            dialog.dismiss()
                        }.build())
                    .build().show(supportFragmentManager, "list")
            }
            btnAddress.setOnClickListener {
                BeautyDialog.Builder()
                    .setDialogPosition(POS_BOTTOM)
                    .setDialogMargin(UView.dp2px(8f))
                    .setDialogTitle(SimpleTitle.get("地址对话框"))
                    .setDialogContent(AddressContent.Builder()
                        .setMaxLevel(LEVEL_AREA)
                        .setOnAddressSelectedListener { dialog: BeautyDialog, province: String, city: String?, area: String? ->
                            toast("$province - $city - $area")
                            dialog.dismiss()
                        }.build())
                    .build().show(supportFragmentManager, "list")
            }
            btnContent.setOnClickListener {
                BeautyDialog.Builder()
                    .setOutCancelable(true)
                    .setDialogPosition(POS_BOTTOM)
                    .setDialogTitle(SimpleTitle.get("简单内容对话框"))
                    .setDialogContent(SimpleContent.get("君不見黃河之水天上來，奔流到海不復回。 君不見高堂明鏡悲白髮，朝如青絲暮成雪。 人生得意須盡歡，莫使金樽空對月。 天生我材必有用，千金散盡還復來。 烹羊宰牛且爲樂，會須一飲三百杯。 岑夫子，丹丘生。將進酒，杯莫停。 與君歌一曲，請君爲我側耳聽。 鐘鼓饌玉不足貴，但願長醉不願醒。 古來聖賢皆寂寞，惟有飲者留其名。 陳王昔時宴平樂，斗酒十千恣讙謔。 主人何為言少錢？徑須沽取對君酌。 五花馬，千金裘。呼兒將出換美酒，與爾同銷萬古愁。"))
                    .build().show(supportFragmentManager, "list")
            }
            btnCustomList.setOnClickListener {
                val adapter = getAdapter(R.layout.uix_dialog_content_list_simple_item,
                    { helper, item: SimpleList.Item ->
                        val tv = helper.getView<NormalTextView>(R.id.tv)
                        tv.text = item.content
                        item.gravity?.let { tv.gravity = it }
                        item.icon?.let { helper.setImageDrawable(R.id.iv, it) }
                    },
                    emptyList())
                val ev = EmptyView.Builder(this@DialogActivity)
                    .setEmptyLoadingTips("loading")
                    .setEmptyLoadingTipsColor(Color.BLUE)
                    .setLoadingStyle(LoadingStyle.STYLE_IOS)
                    .setEmptyViewState(EmptyViewState.STATE_LOADING)
                    .build()
                customList = CustomList.Builder(this@DialogActivity)
                    .setEmptyView(ev)
                    .setAdapter(adapter)
                    .build()
                adapter.onItemDebouncedClick { _, _, pos ->
                    val item = adapter.data[pos]
                    toast("${item.id} : ${item.content}")
                    customList?.getDialog()?.dismiss()
                }
                BeautyDialog.Builder()
                    .setFixedHeight(ViewUtils.getScreenHeight() / 2)
                    .setOutCancelable(true)
                    .setDialogPosition(POS_BOTTOM)
                    .setDialogTitle(SimpleTitle.get("自定义列表对话框"))
                    .setDialogContent(customList!!)
                    .build().show(supportFragmentManager, "custom-list")
                // 先显示对话框再加载数据的情形
                Handler().postDelayed({
                    ev.hide()
                    adapter.setNewInstance(listOf(
                        SimpleList.Item(0,
                            "第 1 项",
                            ResUtils.getDrawable(R.drawable.uix_eye_close_48)),
                        SimpleList.Item(1,
                            "第 2 项",
                            ResUtils.getDrawable(R.drawable.uix_eye_open_48)),
                        SimpleList.Item(2,
                            "第 3 项",
                            ResUtils.getDrawable(R.drawable.uix_close_black_24dp)),
                        SimpleList.Item(3, "第 4 项", ResUtils.getDrawable(R.drawable.uix_loading)),
                        SimpleList.Item(4,
                            "第 5 项",
                            ResUtils.getDrawable(R.drawable.uix_eye_close_48)),
                        SimpleList.Item(5,
                            "第 6 项",
                            ResUtils.getDrawable(R.drawable.uix_eye_open_48)),
                        SimpleList.Item(6,
                            "第 7 项",
                            ResUtils.getDrawable(R.drawable.uix_close_black_24dp)),
                        SimpleList.Item(7, "第 8 项", ResUtils.getDrawable(R.drawable.uix_loading)),
                        SimpleList.Item(8,
                            "第 9 项",
                            ResUtils.getDrawable(R.drawable.uix_eye_close_48)),
                        SimpleList.Item(9,
                            "第 10 项",
                            ResUtils.getDrawable(R.drawable.uix_eye_open_48)),
                        SimpleList.Item(10,
                            "第 11 项",
                            ResUtils.getDrawable(R.drawable.uix_close_black_24dp)),
                        SimpleList.Item(11, "第 12 项", ResUtils.getDrawable(R.drawable.uix_loading)),
                        SimpleList.Item(12,
                            "第 13 项",
                            ResUtils.getDrawable(R.drawable.uix_eye_close_48)),
                        SimpleList.Item(13,
                            "第 14 项",
                            ResUtils.getDrawable(R.drawable.uix_eye_open_48)),
                        SimpleList.Item(14,
                            "第 15 项",
                            ResUtils.getDrawable(R.drawable.uix_close_black_24dp)),
                        SimpleList.Item(15, "第 16 项", ResUtils.getDrawable(R.drawable.uix_loading))
                    ).toMutableList())
                }, 3000)
            }
            btnNotCancelable.setOnClickListener {
                BeautyDialog.Builder()
                    .setBackCancelable(false)
                    .setOutCancelable(false)
                    .setDialogStyle(STYLE_WRAP)
                    .setDialogBackground(null)
                    .setDialogContent(UpgradeContent())
                    .build().show(supportFragmentManager, "not cancelable")
            }
            btnRateIntro.onDebouncedClick {
                RatingManager.marketTitle = "测试修改标题"
                RatingManager.rateApp({ toast("跳转到反馈页") },
                    { toast("跳转到应用市场评分") },
                    supportFragmentManager)
            }
            btnSimpleGrid.onDebouncedClick {
                BeautyDialog.Builder()
                    .setDialogMargin(0)
                    .setDialogPosition(POS_BOTTOM)
                    .setDialogTitle(SimpleTitle.get("简单的网格"))
                    .setDialogContent(
                        SimpleGrid.Builder(R.layout.item_tool_color) { helper: BaseViewHolder, item: Int ->
                            helper.getView<CircleImageView>(R.id.iv).setFillingCircleColor(item)
                        }.setList(listOf(
                            ResUtils.getColor(R.color.tool_item_color_1),
                            ResUtils.getColor(R.color.tool_item_color_2),
                            ResUtils.getColor(R.color.tool_item_color_3),
                            ResUtils.getColor(R.color.tool_item_color_4),
                            ResUtils.getColor(R.color.tool_item_color_5),
                            ResUtils.getColor(R.color.tool_item_color_6),
                            ResUtils.getColor(R.color.tool_item_color_7),
                            ResUtils.getColor(R.color.tool_item_color_8),
                            ResUtils.getColor(R.color.tool_item_color_9),
                            ResUtils.getColor(R.color.tool_item_color_10),
                            ResUtils.getColor(R.color.tool_item_color_11),
                            ResUtils.getColor(R.color.tool_item_color_12)
                        )).setOnItemClickListener { _: BeautyDialog, item: Int ->
                            toast("$item")
                        }.setSpanCount(5).build()
                    ).build().show(supportFragmentManager, "grid")
            }

        }
    }

    override fun initData() {
    }

    override fun startObserve() {
    }
}