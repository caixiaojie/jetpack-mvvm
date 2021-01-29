package com.cxj.jetpackmvvm

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.qmuiteam.qmui.widget.dialog.QMUIDialog

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.cxj.jetpackmvvm", appContext.packageName)
    }

    @Test
    fun testMessageDialog() {
        QMUIDialog.MessageDialogBuilder(App.CONTEXT)
            .setMessage("这是弹窗哦")
            .addAction("取消") { dialog, index -> dialog.dismiss()}
            .addAction("确定") { dialog, index -> dialog.dismiss()}
            .setCancelable(false)
            .setCanceledOnTouchOutside(false)
            .create().show()
    }
}