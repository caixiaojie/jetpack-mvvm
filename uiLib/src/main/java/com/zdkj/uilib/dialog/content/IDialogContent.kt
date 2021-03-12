package com.zdkj.uilib.dialog.content

import android.content.Context
import android.view.View
import com.zdkj.uilib.dialog.BeautyDialog
import com.zdkj.uilib.dialog.footer.IDialogFooter
import com.zdkj.uilib.dialog.title.IDialogTitle

/**
 * The dialog content interface
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 16:14
 */
interface IDialogContent {

    /** Override to set the dialog content view */
    fun getView(ctx: Context): View

    /** Override to get the dialog */
    fun setDialog(dialog: BeautyDialog) { }

    /** Override to get the dialog title */
    fun setDialogTitle(dialogTitle: IDialogTitle?) { }

    /** Override to get the dialog footer */
    fun setDialogFooter(dialogFooter: IDialogFooter?) { }
}