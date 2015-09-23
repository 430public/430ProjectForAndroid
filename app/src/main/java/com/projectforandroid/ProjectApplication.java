package com.projectforandroid;

import android.app.Application;
import android.content.Context;
import com.projectforandroid.imageloader.ImageLoaderCache;

/**
 * Created by 大灯泡 on 2015/9/19.
 * 全局部署
 */
public class ProjectApplication extends Application {
    public static Context context;
    public static String version;

    @Override
    public void onCreate() {
        super.onCreate();
        ProjectApplication.context = getApplicationContext();
        ProjectApplication.version = "1.0.0";
        ImageLoaderCache.getInstance().initImageLoader();
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
}
