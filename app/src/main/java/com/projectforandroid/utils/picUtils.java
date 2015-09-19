package com.projectforandroid.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import com.projectforandroid.ProjectApplication;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 大灯泡 on 2015/9/19.
 * 图片工具类
 */
public class picUtils {

    public static BitmapDrawable background(int imgId) {
        //imgID为drawable里面的图片ID
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = ProjectApplication.context.getResources().openRawResource(imgId);
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
        try {
            is.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new BitmapDrawable(ProjectApplication.context.getResources(), bitmap);
    }
}
