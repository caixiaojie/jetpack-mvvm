package com.cxj.av.day1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zdkj.ktx.utils.AssetsUtils;

import java.io.File;

/**
 * Paint.setAntiAlias(boolean flag);//设置抗锯齿效果 设置true的话边缘会将锯齿模糊化
 * Paint.setDither(boolean flag);//设置防抖动,设置true的话图片看上去会更柔和点
 * Paint.setColor(int color);//设置画笔颜色
 * ###TODO
 * Paint.setARGB(int a, int r, int g, int b); //设置画笔的ARGB值
 * Paint.setAlpha(int alpha);//设置画笔的Alpha值
 * Paint.setStyle(); //设置画笔的style (三种：FILL填充 FILL_AND_STROKE填充加描边 STROKE描边 )
 * Paint.setStrokeWidth(float width);//设置描边宽度
 *
 * Paint.setXfermode(Xfermode xfermode);//设置图形重叠时的处理方式，如合并，取交集或并集，经常用来制作橡皮的擦除效果
 * Paint.setShader(Shader shader);//设置图像效果，使用Shader可以绘制出各种渐变效果
 * Paint.setShadowLayer(float radius ,float dx,float dy,int color);//在图形下面设置阴影层，产生阴影效果，radius为阴影的半径，dx和dy为阴影在x轴和y轴上的距离，color为阴影的颜色
 * //下面写文本的时候经常用到的
 * Paint.setTextSize(float textSize);//设置画笔文字大小
 * Paint.measureText(String text);//测试文本的长度
 * Paint.setTextAlign(Paint.Align align);// CENTER(文本居中) LEFT(文本左对齐) RIGHT(文本右对齐)
 */
public class CustomView extends View {
    private Paint paint;
    private Bitmap bitmap;
    public CustomView(Context context) {
        this(context,null,0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        File dir = context.getExternalFilesDir("");
        File file = new File(dir, "default_bj.png");
        if (!file.exists()) {
            AssetsUtils.copyFileFromAssets(context, "default_bj.png", file.getPath());
        }
        String defaultPath = file.getPath();
        bitmap = BitmapFactory.decodeFile(defaultPath);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(bitmap,0,0,paint);
        }
    }
}
