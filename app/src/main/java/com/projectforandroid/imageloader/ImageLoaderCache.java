package com.projectforandroid.imageloader;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.projectforandroid.ProjectApplication;
import java.io.File;

/**
 * Created by 大灯泡 on 2015/9/21.
 */
public class ImageLoaderCache {

    /*String imageUri = "http://site.com/image.png"; // from Web
      String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
      String imageUri = "content://media/external/audio/albumart/13"; // from content provider
      String imageUri = "assets://image.png"; // from assets
      String imageUri = "drawable://" + R.drawable.image; // from drawables (only images, non-9patch)  */
    //缓存地址
    private static final File cacheDir =
        StorageUtils.getOwnCacheDirectory(ProjectApplication.context, "430project/Cache/Bitmap");
    private static ImageLoader mLoader = ImageLoader.getInstance();
    private ImageLoaderConfiguration configuration;

    private static ImageLoaderCache mLoaderCache = null;

    public static ImageLoaderCache getInstance() {
        if (mLoaderCache == null) mLoaderCache = new ImageLoaderCache();
        return mLoaderCache;
    }

    public ImageLoader getImageLoader() {
        if (mLoader == null) mLoader = ImageLoader.getInstance();
        return mLoader;
    }

    //------------------------------------------初始化，在全局函数调用---------------------------------------------
    public void initImageLoader() {
        configuration = new ImageLoaderConfiguration.Builder(ProjectApplication.context).diskCache(
            new UnlimitedDiskCache(cacheDir))//配置缓存路径
            .memoryCacheExtraOptions(480, 800)//每张图片在内存的最大宽高
            .threadPoolSize(3)//线程池大小，最多3个线程
            .memoryCache(new WeakMemoryCache()).threadPriority(Thread.NORM_PRIORITY)//优先级普通
            .diskCacheSize(30 * 1024 * 1024)//磁盘缓存大小30M
            .diskCacheFileNameGenerator(new Md5FileNameGenerator())//MD5保存文件名
            .diskCacheFileCount(100)//最多100张图片
            .writeDebugLogs()//写入日志
            .build();
        mLoader.init(configuration);
    }

    //------------------------------------------Getter-----------------------------------------------

    public ImageLoaderConfiguration getConfiguration() {
        return configuration;
    }

    //------------------------------------------加载图片方法-----------------------------------------------
    public static void loadImage(String url, ImageView imageView, DisplayImageOptions options) {
        mLoader.displayImage(url, imageView, options);
    }

    public static void loadImage(String url, ImageView imageView) {
        mLoader.displayImage(url, imageView);
    }

    public static void loadImage(String url, ImageView imageView, DisplayImageOptions options,
        ImageLoadingListener listener) {
        mLoader.displayImage(url, imageView, options, listener);
    }

    //------------------------------------------判断内存缓存是否有图片-----------------------------------------------
    public Bitmap getMemoryCache(String key) {
        Bitmap bm = mLoader.getMemoryCache().get(key);
        if (bm != null && !bm.isRecycled()) return bm;
        return null;
    }

    //------------------------------------------判断磁盘缓存是否有图片-----------------------------------------------
    public File getDiskCache(String key) {
        File file = mLoader.getDiskCache().get(key);
        if (file != null && file.exists()) return file;
        return null;
    }
}
