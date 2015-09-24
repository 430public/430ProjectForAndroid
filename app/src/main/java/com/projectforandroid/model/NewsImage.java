package com.projectforandroid.model;

/**
 * Created by exkulo on 9/23/2015.
 */
public class NewsImage {

    int width;
    int height;
    String url;

    /**
     * 图片高度
     * @return
     */
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 图片地址
     * @return
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 图片宽度
     * @return
     */
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
