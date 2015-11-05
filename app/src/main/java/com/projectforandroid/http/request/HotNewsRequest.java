package com.projectforandroid.http.request;

import android.content.Context;
import com.projectforandroid.ProjectApplication;
import com.projectforandroid.data.HotNewBean;
import com.projectforandroid.http.BaseHttpRequest;
import com.projectforandroid.http.respon.BaseResponse;
import com.projectforandroid.utils.DataUtils;
import com.projectforandroid.utils.MD5Tools;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 杰 on 2015/10/11.
 */
public class HotNewsRequest extends BaseHttpRequest {
    private Context mContext;
    private int id;
    private int rows;
    private int classify;

    public HotNewsRequest(Context context, int id, int rows, int classify) {
        mContext = context;
        this.id = id;
        this.rows = rows;
        this.classify = classify;
    }

    public HotNewsRequest() {
    }

    @Override
    public String getUrl() {
        return String.format(Locale.getDefault(), "%snews?id=%d&rows=%d&classify=%d",
            ProjectApplication.getHotNewsBaseUrl(), id, rows, classify);
    }

    @Override
    public String getKey() {
        return getUrl();
    }

    @Override
    public void getResponseData(BaseResponse response, JSONObject json) throws JSONException {
        if (response.getStatus() != 0) {
            JSONArray array = json.optJSONArray("tngou");
            if (array != null && array.length() > 0) {
                HotNewBean bean = new HotNewBean();
                List<HotNewBean.HotNewBeanResult> list = new ArrayList<>(array.length());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.optJSONObject(i);
                    HotNewBean.HotNewBeanResult result = new HotNewBean.HotNewBeanResult();
                    result.count = obj.optInt("count");
                    result.description = obj.optString("description");
                    result.fcount = obj.optInt("fcount");
                    result.fromname = obj.getString("fromname");
                    result.fromurl = obj.optString("fromurl");
                    result.img = String.format(Locale.getDefault(),
                        ProjectApplication.getHotNewsImgBaseUrl(), obj.optString("img"));
                    result.keywords = obj.optString("keywords");
                    result.message = obj.optString("message");
                    result.time = obj.optLong("time");
                    result.rcount = obj.optInt("rcount");
                    result.title = obj.optString("title");
                    result.topclass = obj.optInt("topclass");
                    list.add(result);
                }
                bean.setHotNewBeans(list);
                response.setData(bean);
            }
        }
    }

    //------------------------------------------temp-----------------------------------------------

    public int getClassify() {
        return classify;
    }

    public void setClassify(int classify) {
        this.classify = classify;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public Object LoadCache() {
        if (memoryCache.getJsonFromMemoryCache(MD5Tools.hashKey(getKey())) != null) {
            JSONObject object = memoryCache.getJsonFromMemoryCache(MD5Tools.hashKey(getKey()));
            if (object != null && mBaseResponse != null) {
                mBaseResponse.setStatus(1);
                try {
                    getResponseData(mBaseResponse, object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                JSONObject object;
                object = mDiskCache.getJsonCache(
                    (String) DataUtils.getSharedPreferenceData(ProjectApplication.sharedPreferences,
                        MD5Tools.hashKey(getKey()), MD5Tools.hashKey(getKey())));
                if (object != null && mBaseResponse != null) {
                    mBaseResponse.setStatus(1);
                    getResponseData(mBaseResponse, object);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mBaseResponse.getData() != null) {
                return mBaseResponse.getData();
            } else {
            }
        }
        return null;
    }
}
