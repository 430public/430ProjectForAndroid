package com.projectforandroid.imageloader;

import android.widget.ImageView;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.projectforandroid.ProjectApplication;
import java.io.File;

/**
 * Created by 大灯泡 on 2015/9/21.
 */
public class ImageLoaderCache {

    //缓存地址
    private static final File cacheDir =
        StorageUtils.getOwnCacheDirectory(ProjectApplication.context, "430project/Cache/Bitmap");
    //虽然imageloader是单例，但也加个final以防万一
    private static final ImageLoader mLoader = ImageLoader.getInstance();


    private static ImageLoaderCache mLoaderCache = null;

    public static ImageLoaderCache getInstance() {
        if (mLoaderCache == null) mLoaderCache = new ImageLoaderCache();
        return mLoaderCache;
    }

    //------------------------------------------初始化，在全局函数调用---------------------------------------------
    public void initImageLoader() {
        ImageLoaderConfiguration configuration =
            new ImageLoaderConfiguration.Builder(ProjectApplication.context).diskCache(
                new UnlimitedDiskCache(cacheDir))//配置缓存路径
                .memoryCacheExtraOptions(480, 800)//每张图片在内存的最大宽高
                .threadPoolSize(3)//线程池大小，最多3个线程
                .memoryCache(new WeakMemoryCache()).threadPriority(Thread.NORM_PRIORITY)//优先级普通
                .diskCacheSize(30 * 1024 * 1024)//磁盘缓存大小30M
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//MD5保存文件名
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())//MD5后再用hash加密
                .diskCacheFileCount(100)//最多100张图片
                .writeDebugLogs()//写入日志
                .build();
        mLoader.init(configuration);
    }

    //------------------------------------------temp-----------------------------------------------
    public static void loadImageInView(String uri, ImageView imageView,
        DisplayImageOptions options) {
        mLoader.displayImage(uri, imageView, options);
    }

    public static void loadImageInView(String uri, ImageView imageView) {
        mLoader.displayImage(uri, imageView);
    }

    public static void loadImageWithListener(String uri, ImageAware imageAware,
        DisplayImageOptions options, ImageLoadingListener listener) {
        mLoader.displayImage(uri, imageAware, options, listener);
    }
}
