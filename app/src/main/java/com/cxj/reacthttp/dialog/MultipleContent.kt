package com.cxj.reacthttp.dialog

import android.content.Context
import android.view.View
import com.cxj.jetpackmvvm.R
import com.zdkj.uilib.dialog.content.IDialogContent

/**
 *
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 17:51
 */
class MultipleContent : IDialogContent {

    override fun getView(ctx: Context): View = View.inflate(ctx, R.layout.layout_dialog_content_multiple, null)

}