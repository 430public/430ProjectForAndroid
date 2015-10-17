package com.projectforandroid.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 杰 on 2015/10/14.
 */
public class EntertainmentBean implements Serializable {
    private List<EntertainmentBeanResult> mEntertainmentBeans;

    public EntertainmentBean() {}

    public List<EntertainmentBeanResult> getEntertainmentBeans() {
        return mEntertainmentBeans;
    }

    public void setEntertainmentBeans(List<EntertainmentBeanResult> EntertainmentBeans) {
        mEntertainmentBeans =  EntertainmentBeans;
    }

    public static class EntertainmentBeanResult {
        public String time;
        public String msg;
        public String title;
        public String description;
        public String picUrl;
        public String url;
    }
}
