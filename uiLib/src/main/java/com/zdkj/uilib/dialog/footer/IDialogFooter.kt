package com.zdkj.uilib.dialog.footer

import android.content.Context
import android.view.View
import com.zdkj.uilib.dialog.BeautyDialog
import com.zdkj.uilib.dialog.content.IDialogContent
import com.zdkj.uilib.dialog.title.IDialogTitle

/**
 * Dialog footer interace
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 16:15
 */
interface IDialogFooter {

    /** Get the dialog footer view */
    fun getView(ctx: Context): View

    /** Override to get the dialog */
    fun setDialog(dialog: BeautyDialog) { }

    /** Override to get the dialog title */
    fun setDialogTitle(dialogTitle: IDialogTitle?) { }

    /** Override to get the dialog content */
    fun setDialogContent(dialogContent: IDialogContent?) { }
}
