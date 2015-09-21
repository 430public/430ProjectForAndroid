package com.projectforandroid.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.projectforandroid.ProjectApplication;
import com.projectforandroid.R;
import com.projectforandroid.imageloader.ImageLoaderCache;
import com.projectforandroid.utils.BitmapUtils;
import com.projectforandroid.utils.fileutils.FileUtils;

/**
 * Created by 大灯泡 on 2015/9/21.
 */
public class GeneralImageView extends ImageView {
    private String imageUrl="";//图片url
    private int defaultPic= R.mipmap.ic_launcher;//默认图片
    private float roundPx=0;//圆角度
    private ImageLoader mLoader;
    private ImageLoaderCache mImageLoaderCache;
    private String url="";

    public GeneralImageView(Context context) {
        this(context, null);
    }

    public GeneralImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GeneralImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mImageLoaderCache=ImageLoaderCache.getInstance();
        mLoader=mImageLoaderCache.getImageLoader();
    }

    //===========================加载图片==================================
    public void loadImage(String imageUrl){
        this.imageUrl=imageUrl;
        ImageLoaderCache.loadImage(imageUrl,this);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        try {
            BitmapDrawable drawable = (BitmapDrawable) getDrawable();
            if (drawable == null) {

                if (imageUrl.length() > 0)
                    mImageLoaderCache.loadImage(imageUrl,this);
            } else {
                if (drawable.getBitmap() == null
                    || drawable.getBitmap().isRecycled()) {
                    mImageLoaderCache.loadImage(imageUrl,this);
                }
            }
        } catch (Exception e) {

        }

        try {
            super.onDraw(canvas);
        } catch (RuntimeException ex) {
        }
    }

    //------------------------------------------Setter/Getter------------------------------------------

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getDefaultPic() {
        return defaultPic;
    }

    public void setDefaultPic(int defaultPic) {
        this.defaultPic = defaultPic;
        this.url="drawable://" + defaultPic;
    }

    public float getRoundPx() {
        return roundPx;
    }

    public void setRoundPx(float roundPx) {
        this.roundPx = roundPx;
    }
}
