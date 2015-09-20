package com.projectforandroid.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.projectforandroid.ProjectApplication;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 大灯泡 on 2015/9/19.
 * 图片工具类
 */
public class BitmapUtils {

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

    /**
     * 转换图片成圆形，可能会内存溢出
     *
     * @param bitmap 传入Bitmap对象
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        // 得到宽度，高度
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 半径
        float radius;
        // 原绘图区域
        float left, top, right, bottom;
        // 目标绘图区域
        float dst_left, dst_top, dst_right, dst_bottom;
        // 分情况进行
        if (width <= height) {
            // 半径
            radius = width / 2;
            // 上边界和左边界为0，下边界和右边界为直径
            top = 0;
            bottom = 2 * radius;
            left = 0;
            right = 2 * radius;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            // 半径
            radius = height / 2;
            // 因为width>height，因此左边界绝对无法为0，肯定与原点有一定差距
            // 这个差距=（宽-高）/2，因为两边都有，所以除以2
            float clip = (width - height) / 2;
            // 左边界=0+差距，右边界=宽-差距，上边界=0，下边界=半径
            left = 0 + clip;
            right = width - clip;
            top = 0;
            bottom = 2 * radius;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        // 蒙板
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        // 原绘图区域
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        // 新绘图区域
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        // 抗锯齿
        paint.setAntiAlias(true);
        // 填充透明黑
        canvas.drawARGB(0, 0, 0, 0);
        // 绘制圆角矩形，x,y方向圆角均为圆的半径，得出一个圆形
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        // 回收
        bitmap.recycle();
        return output;
    }

    /**
     * 转换bitmap为byte[]
     */
    public static byte[] bitmapToByte(Bitmap b) {
        if (b == null) {
            return null;
        }

        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(CompressFormat.PNG, 100, o);
        return o.toByteArray();
    }

    /**
     * 转换byte[]为bitmap
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * drawable转bitmap
     */
    public static Bitmap drawableToBitmap(Drawable d) {
        return d == null ? null : ((BitmapDrawable) d).getBitmap();
    }

    /**
     * bitmap转drawable
     */
    public static Drawable bitmapToDrawable(Bitmap b) {
        return b == null ? null : new BitmapDrawable(b);
    }

    /**
     * drawable转byte[]
     */
    public static byte[] drawableToByte(Drawable d) {
        return bitmapToByte(drawableToBitmap(d));
    }

    /**
     * byte[]转drawable
     */
    public static Drawable byteToDrawable(byte[] b) {
        return bitmapToDrawable(byteToBitmap(b));
    }

    /**
     * 缩放图片
     */
    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
        return scaleImage(org, (float) newWidth / org.getWidth(),
            (float) newHeight / org.getHeight());
    }

    /**
     * 缩放图片
     *
     * @param scaleWidth 目标宽度
     * @param scaleHeight 目标高度
     */
    private static Bitmap scaleImage(Bitmap org, float scaleWidth, float scaleHeight) {
        if (org == null) {
            return null;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
    }

    /**保存bitmap*/
    public static boolean saveBitmap(File file, Bitmap bitmap){
        if(file == null || bitmap == null)
            return false;
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
            return bitmap.compress(CompressFormat.JPEG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
