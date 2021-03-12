package com.zdkj.ktx.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;

import androidx.annotation.ArrayRes;
import androidx.annotation.BoolRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.PluralsRes;
import androidx.annotation.RawRes;
import androidx.annotation.StringRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import java.io.InputStream;

/**
 * <pre>
 *      author: 二哈
 *      date: 2021/3/9
 *      des:
 * </pre>
 */
public final class ResUtils {

    public static float getAttrFloatValue(Context context, int attrRes){
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrRes, typedValue, true);
        return typedValue.getFloat();
    }

    public static int getAttrColor(Context context, int attrRes){
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrRes, typedValue, true);
        return typedValue.data;
    }

    public static ColorStateList getAttrColorStateList(Context context, int attrRes){
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrRes, typedValue, true);
        return ContextCompat.getColorStateList(context, typedValue.resourceId);
    }

    public static Drawable getAttrDrawable(Context context, int attrRes){
        int[] attrs = new int[] { attrRes };
        TypedArray ta = context.obtainStyledAttributes(attrs);
        Drawable drawable = getAttrDrawable(context, ta, 0);
        ta.recycle();
        return drawable;
    }

    public static Drawable getAttrDrawable(Context context, TypedArray typedArray, int index){
        TypedValue value = typedArray.peekValue(index);
        if (value != null) {
            if (value.type != TypedValue.TYPE_ATTRIBUTE && value.resourceId != 0) {
                return AppCompatResources.getDrawable(context, value.resourceId);
            }
        }
        return null;
    }

    public static int getAttrDimen(Context context, int attrRes){
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrRes, typedValue, true);
        return TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
    }

    /*----------------------------------wrapper methods--------------------------------------*/

    public static int[] getIntArray(@ArrayRes int id) {
        return AppGlobals.getApplication().getResources().getIntArray(id);
    }

    public static CharSequence[] getTextArray(@ArrayRes int id) {
        return AppGlobals.getApplication().getResources().getTextArray(id);
    }

    public static String[] getStringArray(@ArrayRes int id) {
        return AppGlobals.getApplication().getResources().getStringArray(id);
    }

    @ColorInt
    public static int getColor(@ColorRes int id) {
        return AppGlobals.getApplication().getResources().getColor(id);
    }

    public static String getString(@StringRes int id) {
        return AppGlobals.getApplication().getResources().getString(id);
    }

    public static String getString(@StringRes int id, Object... formatArgs) {
        return AppGlobals.getApplication().getResources().getString(id, formatArgs);
    }

    public static CharSequence getText(@StringRes int id) {
        return AppGlobals.getApplication().getResources().getText(id);
    }

    public static CharSequence getQuantityText(@PluralsRes int id, int quantity) {
        return AppGlobals.getApplication().getResources().getQuantityText(id, quantity);
    }

    public static String getQuantityString(@PluralsRes int id, int quantity) {
        return AppGlobals.getApplication().getResources().getQuantityString(id, quantity);
    }

    public static String getQuantityString(@PluralsRes int id, int quantity, Object... formatArgs) {
        return AppGlobals.getApplication().getResources().getQuantityString(id, quantity, formatArgs);
    }

    public static Drawable getDrawable(@DrawableRes int id) {
        return AppGlobals.getApplication().getResources().getDrawable(id);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static Typeface getFont(@FontRes int id) {
        return AppGlobals.getApplication().getResources().getFont(id);
    }

    public static float getDimension(@DimenRes int id) {
        return AppGlobals.getApplication().getResources().getDimension(id);
    }

    public static boolean getBoolean(@BoolRes int id) {
        return AppGlobals.getApplication().getResources().getBoolean(id);
    }

    public static int getInteger(@IntegerRes int id) {
        return AppGlobals.getApplication().getResources().getInteger(id);
    }

    public static InputStream openRawResource(@RawRes int id) {
        return AppGlobals.getApplication().getResources().openRawResource(id);
    }

    public static AssetFileDescriptor openRawResourceFd(@RawRes int id) {
        return AppGlobals.getApplication().getResources().openRawResourceFd(id);
    }

    public static AssetManager getAssets() {
        return AppGlobals.getApplication().getResources().getAssets();
    }

    /*----------------------------------inner methods--------------------------------------*/

    private ResUtils() {
        throw new UnsupportedOperationException("u can't initialize me!");
    }

}
