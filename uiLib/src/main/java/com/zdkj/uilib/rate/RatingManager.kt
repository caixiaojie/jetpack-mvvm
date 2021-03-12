package com.zdkj.uilib.rate

import android.graphics.Typeface
import android.view.Gravity
import androidx.fragment.app.FragmentManager
import com.zdkj.uilib.R
import com.zdkj.uilib.anno.BottomButtonPosition
import com.zdkj.uilib.anno.DialogStyle
import com.zdkj.uilib.bean.TextStyleBean
import com.zdkj.uilib.dialog.BeautyDialog
import com.zdkj.uilib.dialog.content.IDialogContent
import com.zdkj.uilib.dialog.content.SimpleContent
import com.zdkj.uilib.dialog.footer.SimpleFooter
import com.zdkj.uilib.dialog.title.IDialogTitle
import com.zdkj.uilib.dialog.title.SimpleTitle
import com.zdkj.uilib.utils.URes

/**
 * App rating manager.
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2020-09-05 10:28
 */
object RatingManager {

    var isDarkTheme: Boolean = false

    var title: String = URes.getString(R.string.uix_rating_dialog_title)
    var titleStyle = TextStyleBean().apply { this.gravity = Gravity.CENTER;this.typeFace = Typeface.BOLD }
    var confirmText = URes.getString(R.string.uix_rating_confirm)
    var cancelText = URes.getString(R.string.uix_rating_cancel)
    var confirmTextStyle: TextStyleBean = SimpleFooter.GlobalConfig.rightTextStyle
    var cancelTextStyle: TextStyleBean = SimpleFooter.GlobalConfig.leftTextStyle

    var feedbackTitle = URes.getString(R.string.uix_rating_feedback_title)
    var feedbackTitleStyle = TextStyleBean().apply { this.gravity = Gravity.CENTER;this.typeFace = Typeface.BOLD }
    var feedbackContent = URes.getString(R.string.uix_rating_feedback_content)
    var feedbackConfirm = URes.getString(R.string.uix_rating_feedback_confirm)
    var feedbackConfirmStyle: TextStyleBean = SimpleFooter.GlobalConfig.rightTextStyle
    var feedbackCancel = URes.getString(R.string.uix_rating_feedback_cancel)
    var feedbackCancelStyle: TextStyleBean = SimpleFooter.GlobalConfig.leftTextStyle

    var marketTitle = URes.getString(R.string.uix_rating_market_title)
    var marketTitleStyle = TextStyleBean().apply { this.gravity = Gravity.CENTER;this.typeFace = Typeface.BOLD }
    var marketContent = URes.getString(R.string.uix_rating_market_content)
    var marketConfirm = URes.getString(R.string.uix_rating_market_confirm)
    var marketConfirmStyle: TextStyleBean = SimpleFooter.GlobalConfig.rightTextStyle
    var marketCancel = URes.getString(R.string.uix_rating_market_cancel)
    var marketCancelStyle: TextStyleBean = SimpleFooter.GlobalConfig.leftTextStyle

    /** Show rate App introduction */
    fun rateApp(onFeedback: () -> Unit, onMarket: () -> Unit, fragmentManager: FragmentManager): BeautyDialog {
        val dialog = BeautyDialog.Builder()
                .setDarkDialog(isDarkTheme)
                .setDialogTitle(SimpleTitle.Builder()
                        .setTitle(title)
                        .setTitleStyle(titleStyle).build())
                .setDialogContent(RatingContent())
                .setDialogBottom(SimpleFooter.Builder()
                        .setLeftText(cancelText)
                        .setRightText(confirmText)
                        .setRightTextStyle(confirmTextStyle)
                        .setLeftTextStyle(cancelTextStyle)
                        .setOnClickListener(object : SimpleFooter.OnClickListener {
                            override fun onClick(dialog: BeautyDialog,
                                                 buttonPos: Int,
                                                 dialogTitle: IDialogTitle?,
                                                 dialogContent: IDialogContent?) {
                                if (buttonPos == BottomButtonPosition.BUTTON_POS_RIGHT) {
                                    val rating = (dialogContent as RatingContent).getRating()
                                    if (rating >= 4) {
                                        showMarketDialog(onMarket, fragmentManager)
                                    } else {
                                        showFeedbackDialog(onFeedback, fragmentManager)
                                    }
                                }
                                dialog.dismiss()
                            }
                        }).build()).build()
        dialog.show(fragmentManager, "rate-dialog")
        return dialog
    }

    fun showFeedbackDialog(onFeedback: () -> Unit = {}, manager: FragmentManager): BeautyDialog {
        val dialog = BeautyDialog.Builder()
                .setDarkDialog(isDarkTheme)
                .setDialogStyle(DialogStyle.STYLE_WRAP)
                .setDialogTitle(SimpleTitle.Builder().setTitle(feedbackTitle).setTitleStyle(feedbackTitleStyle).build())
                .setDialogContent(SimpleContent.Builder().setContent(feedbackContent).setGravity(Gravity.CENTER).build())
                .setDialogBottom(SimpleFooter.Builder()
                        .setLeftText(feedbackCancel)
                        .setRightText(feedbackConfirm)
                        .setRightTextStyle(feedbackConfirmStyle)
                        .setLeftTextStyle(feedbackCancelStyle)
                        .setOnClickListener(object : SimpleFooter.OnClickListener {
                            override fun onClick(dialog: BeautyDialog,
                                                 buttonPos: Int,
                                                 dialogTitle: IDialogTitle?,
                                                 dialogContent: IDialogContent?) {
                                if (buttonPos == BottomButtonPosition.BUTTON_POS_RIGHT) {
                                    onFeedback()
                                }
                                dialog.dismiss()
                            }
                        }).build()).build()
        dialog.show(manager, "rate-feedback-dialog")
        return dialog
    }

    fun showMarketDialog(onMarket: () -> Unit, manager: FragmentManager): BeautyDialog {
        val dialog = BeautyDialog.Builder()
                .setDarkDialog(isDarkTheme)
                .setDialogStyle(DialogStyle.STYLE_WRAP)
                .setDialogTitle(SimpleTitle.Builder().setTitle(marketTitle).setTitleStyle(marketTitleStyle).build())
                .setDialogContent(SimpleContent.Builder().setContent(marketContent).setGravity(Gravity.CENTER).build())
                .setDialogBottom(SimpleFooter.Builder()
                        .setLeftText(marketCancel)
                        .setRightText(marketConfirm)
                        .setRightTextStyle(marketConfirmStyle)
                        .setLeftTextStyle(marketCancelStyle)
                        .setOnClickListener(object : SimpleFooter.OnClickListener {
                            override fun onClick(dialog: BeautyDialog,
                                                 buttonPos: Int,
                                                 dialogTitle: IDialogTitle?,
                                                 dialogContent: IDialogContent?) {
                                if (buttonPos == BottomButtonPosition.BUTTON_POS_RIGHT) {
                                    onMarket()
                                }
                                dialog.dismiss()
                            }
                        }).build()).build()
        dialog.show(manager, "rate-market-dialog")
        return dialog
    }
}