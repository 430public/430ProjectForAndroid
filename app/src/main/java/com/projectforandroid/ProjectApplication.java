package com.projectforandroid;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.projectforandroid.cache.DiskCache;
import com.projectforandroid.imageloader.ImageLoaderCache;
import com.projectforandroid.ui.activity.base.BaseActivity;
import com.projectforandroid.utils.camerautils.CameraUtils;
import com.projectforandroid.utils.fileutils.FileUtils;
import com.projectforandroid.utils.stringutils.StringUtils;
import java.io.File;

/**
 * Created by 大灯泡 on 2015/9/19.
 * 全局部署
 */
public class ProjectApplication extends Application {
    public static Context context;
    public static String version;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    //共享handler，用于不同activity之间的数据共享
    private BaseActivity.shareHandler mHandler=null;

    @Override
    public void onCreate() {
        super.onCreate();
        ProjectApplication.context = getApplicationContext();
        ProjectApplication.version = "1.0.0";
        ImageLoaderCache.getInstance().initImageLoader();
        sharedPreferences = context.getSharedPreferences("key", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        long start=System.currentTimeMillis();
        FileUtils.createFolder(getRootPath(), "images");
        CameraUtils.cleanImgs();
        DiskCache.cleanOverCache();
        long end=System.currentTimeMillis();
        Log.d("haoshi",""+(end-start));
    }

    //------------------------------------------代码中得到各种xml属性-----------------------------------------------
    public static int getResColor(int res) {
        return context.getResources().getColor(res);
    }

    public static String getResString(int strRes) {
        return context.getResources().getString(strRes);
    }

    public static float getResDimen(int dimenRes) {
        return context.getResources().getDimension(dimenRes);
    }

    //------------------------------------------各种得到的方法-----------------------------------------------
    public static String getPackName() {
        return context.getPackageName();
    }

    public static String getRootPath() {
        if (FileUtils.isSDexist()) {
            return FileUtils.getSDCardPath() + "430project";
        } else {
            return "/data/data/" + getPackName();
        }
    }

    public static String getSecretKey() {
        return "7c154dde574c47359cc2fec7b3cbc434";
    }

    public static String getHotNewsBaseUrl() {
        return "http://www.tngou.net/api/top/";
    }

    public static String getHotNewsImgBaseUrl() {
        return "http://tnfs.tngou.net/image%s";
    }

    public static String getEntertainmentBaseUrl() {
        return "https://route.showapi.com/198-1";
    }

    public static String getSportNewsBaseUrl() {
        return "https://route.showapi.com/196-1";
    }

    public static String getPhotoImgPaht() {
        return getRootPath() + File.separator + "images";
    }

    public BaseActivity.shareHandler getHandler() {
        return mHandler;
    }

    public void setHandler(BaseActivity.shareHandler handler) {
        mHandler = handler;
    }
}
