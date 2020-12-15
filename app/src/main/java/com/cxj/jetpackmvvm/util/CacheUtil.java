package com.cxj.jetpackmvvm.util;

import com.cxj.jetpackmvvm.App;

/**
 * FileName: CacheUtil
 * Created by zlx on 2020/9/22 9:47
 * Email: 1170762202@qq.com
 * Description:
 */
public class CacheUtil {
    /**
     * 获取系统默认缓存文件夹内的缓存大小
     */
    public static String getTotalCacheSize() {
        long cacheSize = FileUtils.getSize(App.Companion.getCONTEXT().getCacheDir());
        if (FileUtils.isSDCardAlive()) {
            cacheSize += FileUtils.getSize(App.Companion.getCONTEXT().getExternalCacheDir());
        }
        return FileUtils.formatSize(cacheSize);
    }

    public static void clearAllCache() {
        FileUtils.delete(App.Companion.getCONTEXT().getCacheDir());
        if (FileUtils.isSDCardAlive()) {
            FileUtils.delete(App.Companion.getCONTEXT().getExternalCacheDir());
        }
    }
}
