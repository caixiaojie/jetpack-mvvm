package com.cxj.jetpackmvvm.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import com.cxj.jetpackmvvm.App;

public class CommonUtil {
    /***
     * 根据不同转换Context
     */
    private static AppCompatActivity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof AppCompatActivity)
            return (AppCompatActivity) cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper) cont).getBaseContext());

        return null;
    }

    /**
     * 根据String获取得到资源id
     */
    public static int getResource(String imageName) {
        int resId = App.Companion.getCONTEXT().getResources().getIdentifier(imageName, "mipmap",
                App.Companion.getCONTEXT().getPackageName());
        return resId;
    }

    /**
     * 跳转打开通知权限
     * @param activity
     */
    public static void gotoNotificationSetting(Activity activity) {
        NotificationManagerCompat manager = NotificationManagerCompat.from(activity);
        // areNotificationsEnabled方法的有效性官方只最低支持到API 19，低于19的仍可调用此方法不过只会返回true，即默认为用户已经开启了通知。
        boolean isOpened = manager.areNotificationsEnabled();

        if (!isOpened) {
            new AlertDialog.Builder(activity)
                    .setCancelable(true)
                    .setTitle("权限")
                    .setMessage("通知权限尚未开启呢，是否打开接收最新的趣卡点消息？")
                    .setPositiveButton("确定", (dialog, which) -> {
                        dialog.dismiss();
                        ApplicationInfo appInfo = activity.getApplicationInfo();
                        String pkg = activity.getApplicationContext().getPackageName();
                        int uid = appInfo.uid;
                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                                //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
                                intent.putExtra(Settings.EXTRA_APP_PACKAGE, pkg);
                                intent.putExtra(Settings.EXTRA_CHANNEL_ID, uid);
                                activity.startActivity(intent);
                            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
                                Intent intent = new Intent();
                                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                                intent.putExtra("app_package", activity.getPackageName());
                                intent.putExtra("app_uid", activity.getApplicationInfo().uid);
                                activity.startActivity(intent);
                            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                                activity.startActivity(intent);
                            } else {
                                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                                activity.startActivity(intent);
                            }
                        } catch (Exception e) {
                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            activity.startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                        dialog.dismiss();
                    }).show();
        }
    }
}
