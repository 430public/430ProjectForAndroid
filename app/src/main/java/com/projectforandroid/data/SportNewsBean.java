package com.projectforandroid.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 杰 on 2015/10/15.
 */
public class SportNewsBean implements Serializable{
    private List<SportNewsBeanResult> mSportNewsBeans;

    public SportNewsBean(){}

    public List<SportNewsBeanResult> getSportNewsBeans() {
        return mSportNewsBeans;
    }

    public void setSportNewsBeans(List<SportNewsBeanResult> sportNewsBeans) {
        mSportNewsBeans = sportNewsBeans;
    }

    public static class SportNewsBeanResult{
        public String description;
        public String picUrl;
        public String time;
        public String title;
        public String url;
    }
}
