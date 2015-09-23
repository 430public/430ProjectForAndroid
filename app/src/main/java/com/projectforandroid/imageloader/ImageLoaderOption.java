package com.projectforandroid.imageloader;

import android.graphics.Bitmap.Config;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.projectforandroid.R;

/**
 * Created by 大灯泡 on 2015/9/21.
 */
public class ImageLoaderOption {
    private DisplayImageOptions options;

    private int showImageOnLoading;
    private int showImageForEmptyUri;
    private int showImageOnFail;
    private boolean cacheOnDisk = true;
    private ImageScaleType imageScaleType;
    private Config bitmapConfig=Config.RGB_565;
    private FadeInBitmapDisplayer displayer;
    private RoundedBitmapDisplayer mRoundedBitmapDisplayer;
    public ImageLoaderOption() {
        showImageOnLoading=R.drawable.default_pic;
        showImageForEmptyUri=R.drawable.default_pic;
        showImageOnFail=R.drawable.default_pic;
        cacheOnDisk=true;
        imageScaleType=ImageScaleType.EXACTLY_STRETCHED;
        bitmapConfig=Config.RGB_565;
        displayer=new FadeInBitmapDisplayer(300);
        mRoundedBitmapDisplayer=new RoundedBitmapDisplayer(0);
    }

    public DisplayImageOptions getOptions() {
        return options;
    }

    public void setOptions(DisplayImageOptions options) {
        this.options = options;
    }

    public int getShowImageOnLoading() {
        return showImageOnLoading;
    }

    /** 设置图片在下载期间显示的图片 */
    public void setShowImageOnLoading(int showImageOnLoading) {
        this.showImageOnLoading =
            (showImageOnLoading == 0 ? R.drawable.default_pic : showImageOnLoading);
    }

    public int getShowImageForEmptyUri() {
        return showImageForEmptyUri;
    }

    /** 设置图片Uri为空或是错误的时候显示的图片 */
    public void setShowImageForEmptyUri(int showImageForEmptyUri) {
        this.showImageForEmptyUri =
            (showImageForEmptyUri == 0 ? R.drawable.default_pic : showImageForEmptyUri);
    }

    public int getShowImageOnFail() {
        return showImageOnFail;
    }

    /** 设置图片加载/解码过程中错误时候显示的图片 */
    public void setShowImageOnFail(int showImageOnFail) {
        this.showImageOnFail = (showImageOnFail == 0 ? R.drawable.default_pic : showImageOnFail);
        ;
    }

    public boolean isCacheOnDisk() {
        return cacheOnDisk;
    }

    /** 设置下载的图片是否缓存在SD卡中 */
    public void setCacheOnDisk(boolean cacheOnDisk) {
        this.cacheOnDisk = cacheOnDisk;
    }

    public ImageScaleType getImageScaleType() {
        return imageScaleType;
    }

    /**
     * 设置图片以如何的编码方式显示
     * <ul>
     * <strong>缩放类型mageScaleType</strong>
     * <li>EXACTLY :图像将完全按比例缩小的目标大小</li>
     * <li>EXACTLY_STRETCHED:图片会缩放到目标大小完全</li>
     * <li>IN_SAMPLE_INT:图像将被二次采样的整数倍</li>
     * <li>IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小</li>
     * <li>NONE:图片不会调整</li>
     * </ul>
     */
    public void setImageScaleType(ImageScaleType imageScaleType) {
        this.imageScaleType = (imageScaleType == null ? ImageScaleType.EXACTLY : imageScaleType);
    }

    public Config getBitmapConfig() {
        return bitmapConfig;
    }

    /** 设置图片的解码类型 */
    public void setBitmapConfig(Config bitmapConfig) {
        this.bitmapConfig = (bitmapConfig == null ? Config.RGB_565 : bitmapConfig);
    }

    public FadeInBitmapDisplayer getDisplayer() {
        return displayer;
    }

    public void setRound(int roundPx) {
        this.mRoundedBitmapDisplayer = (mRoundedBitmapDisplayer == null ? mRoundedBitmapDisplayer : new RoundedBitmapDisplayer(roundPx));
    }

    public DisplayImageOptions build() {
        options = new DisplayImageOptions.Builder().showImageOnLoading(showImageOnLoading) //设置图片在下载期间显示的图片
            .showImageForEmptyUri(showImageForEmptyUri)//设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(showImageOnFail)  //设置图片加载/解码过程中错误时候显示的图片
            .cacheInMemory(true)//存入缓存
            .cacheOnDisk(cacheOnDisk)//设置下载的图片是否缓存在SD卡中
            .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
            .imageScaleType(imageScaleType)//设置图片以如何的编码方式显示
            .bitmapConfig(bitmapConfig)//设置图片的解码类型//
            .displayer(mRoundedBitmapDisplayer).build();//构建完成

        return options;
    }
}
