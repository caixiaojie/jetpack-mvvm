package com.cxj.av.day1

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.SurfaceHolder
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.databinding.ActivityCanvasImageBinding
import com.cxj.jetpackmvvm.util.LogUtils
import com.cxj.reacthttp.base.BaseVMActivity
import com.zdkj.ktx.utils.AssetsUtils
import java.io.File

class CanvasImageActivity :  BaseVMActivity(){
    private val binding by binding<ActivityCanvasImageBinding>(R.layout.activity_canvas_image)
    override fun initView() {
        binding.run {
            showImg()
            showImgSurface()
        }
    }

    override fun initData() {
    }

    override fun startObserve() {
    }

    private fun showImg() {
        val dir = getExternalFilesDir("")
        val file = File(dir,"default_bj.png")
        if (!file.exists()) {
            AssetsUtils.copyFileFromAssets(this,"default_bj.png",file.path)
        }
        val decodeFile = BitmapFactory.decodeFile(file.path)
        binding.mIvImg.setImageBitmap(decodeFile)
    }

    private fun showImgSurface() {
        binding.surfaceView.holder.addCallback(object : SurfaceHolder.Callback{
            override fun surfaceCreated(holder: SurfaceHolder) {
                if (holder == null) {
                    return
                }
                val paint = Paint()
                paint.isAntiAlias = true
                val dir = getExternalFilesDir("")
                val file = File(dir,"default_bj.png")
                if (!file.exists()) {
                    AssetsUtils.copyFileFromAssets(this@CanvasImageActivity,"default_bj.png",file.path)
                }
                val decodeFile = BitmapFactory.decodeFile(file.path)
                var canvas: Canvas? = null
                try {
                    canvas = holder.lockCanvas()
                    canvas.drawBitmap(decodeFile,0f,0f,paint)
                }catch (e: Exception) {
                    e.printStackTrace()
                }finally {
                    if (canvas != null) {
                        holder.unlockCanvasAndPost(canvas)
                    }
                }
                LogUtils.d("surfaceCreated: ")
            }

            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
                LogUtils.d("surfaceChanged: ")
            }

            override fun surfaceDestroyed(p0: SurfaceHolder) {
                LogUtils.d("surfaceDestroyed: ")
            }

        })
    }

}