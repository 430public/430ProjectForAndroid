package com.projectforandroid.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 杰 on 2015/10/11.
 */
public class HotNewBean implements Serializable{
    private List<HotNewBeanResult> mHotNewBeans;

    public HotNewBean(){}

    public List<HotNewBeanResult> getHotNewBeans() {
        return mHotNewBeans;
    }

    public void setHotNewBeans(List<HotNewBeanResult> hotNewBeans) {
        mHotNewBeans = hotNewBeans;
    }

    public static class HotNewBeanResult{
        public String title;//资讯标题
        public int topclass;//一级分类
        public String img;//图片
        public String description;//描述
        public String keywords;//关键字
        public String message;//资讯内容
        public int count ;//访问次数
        public int fcount;//收藏数
        public int rcount;//评论读数
        public long time;
        public String fromname;
        public String fromurl;
    }


}
