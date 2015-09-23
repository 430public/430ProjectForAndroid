package com.projectforandroid.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;
import com.projectforandroid.utils.BitmapUtils;
import com.projectforandroid.utils.fileutils.FileUtils;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * Created by 大灯泡 on 2015/9/20.
 * 硬盘缓存
 * 暂时预留，图片使用ImageLoader的缓存机制
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
        final String mKey = key;
        final Bitmap mValue = value;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                saveBitmap(mKey, mValue);
            }
        };
    }

    /** 保存图片 */
    private void saveBitmap(String key, Bitmap value) {
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

    /** 得到磁盘图片缓存路径 */
    public static String getImageDiskCache() {
        return FileUtils.getSDCardPath() + File.separator + "430project" + File.separator
            + cacheName;
    }

    /**
     * 清空缓存
     */
    public void clear() {
        File[] files = cacheDir.listFiles();
        for (File f : files)
            f.delete();
    }

    /**清除缓存*/
    public static void cleanOverTenDayFile() {
        new Thread(cleanRunnable);
    }

    public static void cleanFilter(String path) {
        // 获得文件缓存路径
        File dir = new File(path);
        File files[] = dir.listFiles(new FileOverTenDay());
        if (files != null) {
            for (File file : files) {
                file.delete();
                Log.i("delete cache file", file.getName());
                SystemClock.sleep(200);
            }
        }
    }

    private final static Runnable cleanRunnable = new Runnable() {
        public void run() {
            SystemClock.sleep(20 * 1000);
            String subDir = DiskCache.getImageDiskCache() + File.separator;
            DiskCache.cleanFilter(subDir);
        }
    };

    /** 清除10天前的图片 */
    private static class FileOverTenDay implements FileFilter {
        private long time = System.currentTimeMillis();
        private long n = 10l * 24l * 3600000l; // 10天以前下载的图片

        public boolean accept(File f) {
            if (f != null) {
                long interval = (time - f.lastModified());
                return (interval > n);
            } else {
                return false;
            }
        }
    }
}
