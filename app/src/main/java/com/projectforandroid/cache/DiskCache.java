package com.projectforandroid.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.projectforandroid.utils.BitmapUtils;
import com.projectforandroid.utils.fileutils.FileUtils;
import java.io.File;
import java.io.IOException;

/**
 * Created by 大灯泡 on 2015/9/20.
 * 硬盘缓存
 */
public class DiskCache {

    private static final String TAG = "DiskCache";

    private File cacheDir;  //缓存目录
    private static final String cacheName = "cache";

    /**
     * 构造函数
     */
    public DiskCache(Context context) {
        // 判断缓存目录
        if (FileUtils.isSDexist()) {
            cacheDir =
                new File(FileUtils.getSDCardPath() + File.separator + "430project", cacheName);
        } else {
            cacheDir = context.getCacheDir();
        }
        if (!cacheDir.exists()) cacheDir.mkdirs();

        Log.d(TAG, "缓存地址: " + cacheDir.getAbsolutePath());
    }

    /**
     * 根据key寻找文件
     */
    public File getFile(String key) {
        File f = new File(cacheDir, key);
        if (f.exists()) {
            Log.i(TAG, "文件地址 " + f.getAbsolutePath());
            return f;
        } else {
            Log.e(TAG, "文件不存在");
        }

        return null;
    }

    /**
     * 塞入图片
     */
    public void put(String key, Bitmap value) {
        File f = new File(cacheDir, key);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (BitmapUtils.saveBitmap(f, value)) {
            Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>>>保存成功");
        } else {
            Log.w(TAG, "<<<<<<<<<<<<<<<<<<<<<<保存失败");
        }
    }

    /**
     * 清空缓存
     */
    public void clear() {
        File[] files = cacheDir.listFiles();
        for (File f : files)
            f.delete();
    }
}
