package com.projectforandroid.data;

import android.widget.ImageView;

/**
 * Created by 杰 on 2015/10/8.
 */
public class NewsExample {
    public String title;
    public String content;
    public int newsImageID;
    public String time;

    public NewsExample(String title,String content,int newsImageID,String time) {
        this.title = title;
        this.content = content;
        this.newsImageID = newsImageID;
        this.time = time;
    }
}
