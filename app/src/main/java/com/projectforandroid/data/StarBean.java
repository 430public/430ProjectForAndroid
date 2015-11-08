package com.projectforandroid.data;

import java.io.Serializable;
import org.json.JSONObject;

/**
 * Created by 杰 on 2015/11/2.
 * 读取本地收藏的实体类
 */
public class StarBean implements Serializable {
    private String path;
    private JSONObject json;
    private boolean isStar;

    public StarBean() {
    }

    public StarBean(JSONObject json, String path) {
        this.json = json;
        this.path = path;
    }

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean getIsStar() {
        return isStar;
    }

    public void setIsStar(boolean isStar) {
        this.isStar = isStar;
    }
}
