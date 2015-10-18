package com.projectforandroid.utils.camerautils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import com.projectforandroid.ProjectApplication;
import com.projectforandroid.ui.UIHelper;
import com.projectforandroid.utils.MD5Tools;
import java.io.File;
import java.io.IOException;

/**
 * Created by 大灯泡 on 2015/10/18.
 * 这是一个用于图片拍摄或者选择图片的相机操作工具类
 */
public class CameraUtils {
    public static final int PHOTO_FROM_CAMERA = 233;//来自摄像头拍照的图片
    public static final int PHOTO_FROM_ALBUM = 23;//来自相册选择的照片
    public static final int CROP_PHOTO = 2;//裁剪图片识别码
    private static final String IMAGE_TYPE = "image/*";
    public static Uri photoUri;
    private static File folder = new File(ProjectApplication.getPhotoImgPaht());
    private static String takePhotPath;
    private static String savePhotoPath;

    private CameraUtils() {
        throw new Error("不用new出来哦");
    }

    /** 打开摄像头拍照 */
    public static void getPhtotFromCamera(Context c) {
        try {
            if (!folder.exists()) {
                folder.mkdir();
            }
            String phtotDir = ProjectApplication.getPhotoImgPaht() + "/" + MD5Tools.hashKey(
                "take in " + System.currentTimeMillis());
            File photo = new File(phtotDir);
            setTakePhotPath(phtotDir);
            photo.createNewFile();

            //使用标准的intent去进行视频捕捉
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //将拍到的图片放到指定文件夹
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
            //将uri保存
            photoUri = Uri.fromFile(photo);
            //在onActivityForResult事件中进行相关的处理回调
            ((Activity) c).startActivityForResult(captureIntent, PHOTO_FROM_CAMERA);
        } catch (ActivityNotFoundException e) {
            UIHelper.ToastMessage(c, "抱歉，打开摄像机失败", 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**打开图库*/
    public static void getPhotoFromAlbum(Context c){
        if (!folder.exists()) {
            folder.mkdir();
        }
        String phtotDir = ProjectApplication.getPhotoImgPaht() + "/" + MD5Tools.hashKey(
            "take in " + System.currentTimeMillis());
        File photo = new File(phtotDir);
        setTakePhotPath(phtotDir);
        try {
            photo.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent albumIntent=new Intent(Intent.ACTION_GET_CONTENT);
        albumIntent.setType("image/*");
        //将拍到的图片放到指定文件夹
        albumIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        //将uri保存
        photoUri = Uri.fromFile(photo);
        ((Activity)c).startActivityForResult(albumIntent, PHOTO_FROM_ALBUM);

    }

    //------------------------------------------图片裁剪，调用系统自带的-------------------------------------------

    public static void cropImg(Context c, Uri picUri) {
        try {
            if (!folder.exists()) {
                folder.mkdir();
            }
            String photoDir = ProjectApplication.getPhotoImgPaht() + "/" + "img"+MD5Tools.hashKey(
                "save in " + System.currentTimeMillis())+".png";
            File photo = new File(photoDir);
            setSavePhotoPath(photoDir);
            photo.createNewFile();
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //图片来源
            cropIntent.setDataAndType(picUri, "image/*");
            //设置剪裁剪属性,aspectX，aspectY宽高比
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("scale", true);//缩放
            cropIntent.putExtra("scaleUpIfNeeded", true);//黑边
            //输出的坐标
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //返回剪裁的图片数据
            cropIntent.putExtra("return-data", true);
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
            ((Activity) c).startActivityForResult(cropIntent, CROP_PHOTO);
        } catch (ActivityNotFoundException e) {
            UIHelper.ToastMessage(c, "抱歉，您的手机不支持裁剪功能", 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getTakePhotPath() {
        return takePhotPath+".png";
    }

    public static void setTakePhotPath(String takePhotPath) {
        CameraUtils.takePhotPath = takePhotPath;
    }

    public static String getSavePhotoPath() {
        return savePhotoPath;
    }

    public static void setSavePhotoPath(String savePhotoPath) {
        CameraUtils.savePhotoPath = savePhotoPath;
    }
}
